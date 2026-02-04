package com.inventory.service;

import com.inventory.dto.ProductionReportDTO;
import com.inventory.dto.ProductionSuggestionDTO;
import com.inventory.entity.Product;
import com.inventory.entity.ProductRawMaterial;
import com.inventory.entity.RawMaterial;
import com.inventory.repository.ProductRepository;
import com.inventory.repository.RawMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductionService {
    
    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;
    
    @Transactional(readOnly = true)
    public ProductionReportDTO calculateProduction() {
        List<Product> products = productRepository.findAllWithRawMaterials();
        
        // Sort products by value (descending) - prioritize higher value products
        products.sort((p1, p2) -> p2.getValue().compareTo(p1.getValue()));
        
        // Create a map to track available stock
        Map<Long, Integer> availableStock = new HashMap<>();
        List<RawMaterial> rawMaterials = rawMaterialRepository.findAll();
        for (RawMaterial rm : rawMaterials) {
            availableStock.put(rm.getId(), rm.getStockQuantity());
        }
        
        List<ProductionSuggestionDTO> suggestions = new ArrayList<>();
        BigDecimal totalValue = BigDecimal.ZERO;
        
        // Calculate production for each product
        for (Product product : products) {
            if (product.getProductRawMaterials().isEmpty()) {
                continue;
            }
            
            // Calculate max quantity that can be produced based on available stock
            Integer maxQuantity = calculateMaxQuantity(product, availableStock);
            
            if (maxQuantity > 0) {
                // Update available stock
                for (ProductRawMaterial prm : product.getProductRawMaterials()) {
                    Long rawMaterialId = prm.getRawMaterial().getId();
                    Integer currentStock = availableStock.get(rawMaterialId);
                    Integer newStock = currentStock - (prm.getQuantityRequired() * maxQuantity);
                    availableStock.put(rawMaterialId, newStock);
                }
                
                // Calculate total value for this product
                BigDecimal productTotalValue = product.getValue().multiply(new BigDecimal(maxQuantity));
                totalValue = totalValue.add(productTotalValue);
                
                // Add to suggestions
                ProductionSuggestionDTO suggestion = new ProductionSuggestionDTO();
                suggestion.setProductId(product.getId());
                suggestion.setProductName(product.getName());
                suggestion.setQuantityCanProduce(maxQuantity);
                suggestion.setProductValue(product.getValue());
                suggestion.setTotalValue(productTotalValue);
                
                suggestions.add(suggestion);
            }
        }
        
        ProductionReportDTO report = new ProductionReportDTO();
        report.setSuggestions(suggestions);
        report.setTotalValue(totalValue);
        
        return report;
    }
    
    private Integer calculateMaxQuantity(Product product, Map<Long, Integer> availableStock) {
        Integer maxQuantity = Integer.MAX_VALUE;
        
        for (ProductRawMaterial prm : product.getProductRawMaterials()) {
            Long rawMaterialId = prm.getRawMaterial().getId();
            Integer available = availableStock.getOrDefault(rawMaterialId, 0);
            Integer required = prm.getQuantityRequired();
            
            if (required == 0) {
                continue;
            }
            
            Integer possibleQuantity = available / required;
            maxQuantity = Math.min(maxQuantity, possibleQuantity);
        }
        
        return maxQuantity == Integer.MAX_VALUE ? 0 : maxQuantity;
    }
}
