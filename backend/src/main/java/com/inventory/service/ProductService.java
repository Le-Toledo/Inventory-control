package com.inventory.service;

import com.inventory.dto.ProductDTO;
import com.inventory.dto.ProductRawMaterialDTO;
import com.inventory.entity.Product;
import com.inventory.entity.ProductRawMaterial;
import com.inventory.entity.RawMaterial;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.repository.ProductRawMaterialRepository;
import com.inventory.repository.ProductRepository;
import com.inventory.repository.RawMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;
    private final ProductRawMaterialRepository productRawMaterialRepository;
    
    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        return productRepository.findAllWithRawMaterials().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product product = productRepository.findByIdWithRawMaterials(id);
        if (product == null) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        return convertToDTO(product);
    }
    
    @Transactional
    public ProductDTO create(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setValue(productDTO.getValue());
        
        Product savedProduct = productRepository.save(product);
        
        if (productDTO.getRawMaterials() != null && !productDTO.getRawMaterials().isEmpty()) {
            updateProductRawMaterials(savedProduct, productDTO.getRawMaterials());
        }
        
        return convertToDTO(productRepository.findByIdWithRawMaterials(savedProduct.getId()));
    }
    
    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        product.setName(productDTO.getName());
        product.setValue(productDTO.getValue());
        
        if (productDTO.getRawMaterials() != null) {
            // Remove existing associations
            productRawMaterialRepository.deleteByProductId(id);
            product.getProductRawMaterials().clear();
            
            // Add new associations
            updateProductRawMaterials(product, productDTO.getRawMaterials());
        }
        
        productRepository.save(product);
        return convertToDTO(productRepository.findByIdWithRawMaterials(id));
    }
    
    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
    
    private void updateProductRawMaterials(Product product, List<ProductRawMaterialDTO> rawMaterialsDTO) {
        for (ProductRawMaterialDTO dto : rawMaterialsDTO) {
            RawMaterial rawMaterial = rawMaterialRepository.findById(dto.getRawMaterialId())
                    .orElseThrow(() -> new ResourceNotFoundException("Raw material not found with id: " + dto.getRawMaterialId()));
            
            ProductRawMaterial productRawMaterial = new ProductRawMaterial();
            productRawMaterial.setProduct(product);
            productRawMaterial.setRawMaterial(rawMaterial);
            productRawMaterial.setQuantityRequired(dto.getQuantityRequired());
            
            product.getProductRawMaterials().add(productRawMaterial);
        }
    }
    
    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setValue(product.getValue());
        
        if (product.getProductRawMaterials() != null) {
            List<ProductRawMaterialDTO> rawMaterialsDTO = product.getProductRawMaterials().stream()
                    .map(prm -> {
                        ProductRawMaterialDTO rmDTO = new ProductRawMaterialDTO();
                        rmDTO.setId(prm.getId());
                        rmDTO.setRawMaterialId(prm.getRawMaterial().getId());
                        rmDTO.setRawMaterialName(prm.getRawMaterial().getName());
                        rmDTO.setQuantityRequired(prm.getQuantityRequired());
                        return rmDTO;
                    })
                    .collect(Collectors.toList());
            dto.setRawMaterials(rawMaterialsDTO);
        }
        
        return dto;
    }
}
