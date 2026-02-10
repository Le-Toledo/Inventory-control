package com.inventory.service;

import com.inventory.dto.ProductionReportDTO;
import com.inventory.dto.ProductionSuggestionDTO;
import com.inventory.entity.MateriaPrima;
import com.inventory.entity.Product;
import com.inventory.entity.ProductMateriaPrima;
import com.inventory.repository.MateriaPrimaRepository;
import com.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductionService {

    private final ProductRepository productRepository;
    private final MateriaPrimaRepository materiaPrimaRepository;

    @Transactional(readOnly = true)
    public ProductionReportDTO calculateProduction() {
        return buildReport(false);
    }

    @Transactional
    public ProductionReportDTO consumeProduction() {
        return buildReport(true);
    }

    private ProductionReportDTO buildReport(boolean consumeStock) {
        List<Product> products = productRepository.findAllWithMateriaPrimas();

        // Sort products by value (descending) - prioritize higher value products
        products.sort((p1, p2) -> p2.getValue().compareTo(p1.getValue()));

        // Create a map to track available stock
        Map<Long, Integer> availableStock = new HashMap<>();
        List<MateriaPrima> materiasPrimas = materiaPrimaRepository.findAll();
        for (MateriaPrima materiaPrima : materiasPrimas) {
            availableStock.put(materiaPrima.getId(), materiaPrima.getStockQuantity());
        }

        List<ProductionSuggestionDTO> suggestions = new ArrayList<>();
        BigDecimal totalValue = BigDecimal.ZERO;

        // Calculate production for each product
        for (Product product : products) {
            if (product.getProductMateriaPrimas().isEmpty()) {
                continue;
            }

            // Calculate max quantity that can be produced based on available stock
            Integer maxQuantity = calculateMaxQuantity(product, availableStock);
            int quantityToProduce = maxQuantity;

            if (quantityToProduce > 0) {
                BigDecimal quantityBD = BigDecimal.valueOf(quantityToProduce);
                BigDecimal rawUnitCost = calculateUnitCost(product);
                BigDecimal unitProfit = product.getValue().subtract(rawUnitCost);

                if (consumeStock) {
                    // Consome o estoque corretamente proporcional à produção
                    consumeUnits(product, availableStock, quantityToProduce);
                }

                BigDecimal productTotalValue = product.getValue().multiply(quantityBD);
                totalValue = totalValue.add(productTotalValue);

                BigDecimal totalCost = rawUnitCost.multiply(quantityBD);
                BigDecimal profit = unitProfit.multiply(quantityBD);

                BigDecimal profitMargin = BigDecimal.ZERO;
                if (productTotalValue.compareTo(BigDecimal.ZERO) > 0) {
                    profitMargin = profit
                            .divide(productTotalValue, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100));
                }

                ProductionSuggestionDTO suggestion = new ProductionSuggestionDTO();
                suggestion.setProductId(product.getId());
                suggestion.setProductName(product.getName());
                suggestion.setQuantityCanProduce(quantityToProduce);
                suggestion.setProductValue(product.getValue());
                suggestion.setTotalValue(productTotalValue);
                suggestion.setUnitCost(rawUnitCost);
                suggestion.setUnitProfit(unitProfit);
                suggestion.setTotalCost(totalCost);
                suggestion.setProfit(profit);
                suggestion.setProfitMargin(profitMargin);

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

        for (ProductMateriaPrima prm : product.getProductMateriaPrimas()) {
            Long materiaPrimaId = prm.getMateriaPrima().getId();
            Integer available = availableStock.getOrDefault(materiaPrimaId, 0);
            Integer required = prm.getQuantityRequired();

            if (required == 0) {
                continue;
            }

            Integer possibleQuantity = available / required;
            maxQuantity = Math.min(maxQuantity, possibleQuantity);
        }

        return maxQuantity == Integer.MAX_VALUE ? 0 : maxQuantity;
    }

    private BigDecimal calculateUnitCost(Product product) {
        BigDecimal unitCost = BigDecimal.ZERO;
        for (ProductMateriaPrima prm : product.getProductMateriaPrimas()) {
            BigDecimal materiaPrimaCost = Optional.ofNullable(prm.getMateriaPrima().getUnitCost())
                    .orElse(BigDecimal.ZERO);
            unitCost = unitCost.add(materiaPrimaCost);
        }
        return unitCost;
    }

    private void consumeUnits(Product product, Map<Long, Integer> availableStock, Integer quantityToProduce) {
        for (ProductMateriaPrima prm : product.getProductMateriaPrimas()) {
            MateriaPrima materiaPrima = prm.getMateriaPrima();
            Long materiaPrimaId = materiaPrima.getId();
            Integer currentStock = availableStock.getOrDefault(materiaPrimaId, 0);
            Integer requiredPerUnit = prm.getQuantityRequired();
            int totalRequired = requiredPerUnit * quantityToProduce;
            if (currentStock < totalRequired) {
                totalRequired = currentStock;
            }
            int newStock = currentStock - totalRequired;
            availableStock.put(materiaPrimaId, newStock);
            materiaPrima.setStockQuantity(newStock);
        }
    }
}
