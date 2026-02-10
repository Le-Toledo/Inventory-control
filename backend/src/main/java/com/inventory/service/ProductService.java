package com.inventory.service;

import com.inventory.dto.ProductDTO;
import com.inventory.dto.ProductMateriaPrimaDTO;
import com.inventory.entity.Product;
import com.inventory.entity.ProductMateriaPrima;
import com.inventory.entity.MateriaPrima;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.repository.ProductMateriaPrimaRepository;
import com.inventory.repository.ProductRepository;
import com.inventory.repository.MateriaPrimaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    private final MateriaPrimaRepository materiaPrimaRepository;
    private final ProductMateriaPrimaRepository productMateriaPrimaRepository;
    
    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        return productRepository.findAllWithMateriaPrimas().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Long safeId = Objects.requireNonNull(id, "id is required");
        Product product = productRepository.findByIdWithMateriaPrimas(safeId);
        if (product == null) {
            throw new ResourceNotFoundException("Product not found with id: " + safeId);
        }
        return convertToDTO(product);
    }
    
    @Transactional
    public ProductDTO create(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setValue(productDTO.getValue());
        product.setCode(generateTemporaryCode());

        Product savedProduct = productRepository.save(product);
        savedProduct.setCode(generateProductCode(savedProduct.getId()));
        savedProduct = productRepository.save(savedProduct);
        
        if (productDTO.getMateriasPrimas() != null && !productDTO.getMateriasPrimas().isEmpty()) {
            updateProductMateriasPrimas(savedProduct, productDTO.getMateriasPrimas());
        }
        
        return convertToDTO(productRepository.findByIdWithMateriaPrimas(savedProduct.getId()));
    }
    
    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO) {
        Long safeId = Objects.requireNonNull(id, "id is required");
        Product product = productRepository.findById(safeId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + safeId));
        
        product.setName(productDTO.getName());
        product.setValue(productDTO.getValue());
        
        if (productDTO.getMateriasPrimas() != null) {
            // Remove existing associations
            productMateriaPrimaRepository.deleteByProductId(safeId);
            product.getProductMateriaPrimas().clear();
            
            // Add new associations
            updateProductMateriasPrimas(product, productDTO.getMateriasPrimas());
        }
        
        productRepository.save(product);
        return convertToDTO(productRepository.findByIdWithMateriaPrimas(safeId));
    }
    
    @Transactional
    public void delete(Long id) {
        Long safeId = Objects.requireNonNull(id, "id is required");
        if (!productRepository.existsById(safeId)) {
            throw new ResourceNotFoundException("Product not found with id: " + safeId);
        }
        productRepository.deleteById(safeId);
    }
    
    private void updateProductMateriasPrimas(Product product, List<ProductMateriaPrimaDTO> materiasPrimasDTO) {
        for (ProductMateriaPrimaDTO dto : materiasPrimasDTO) {
            Long materiaPrimaId = Objects.requireNonNull(dto.getMateriaPrimaId(), "materiaPrimaId is required");
            MateriaPrima materiaPrima = materiaPrimaRepository.findById(materiaPrimaId)
                    .orElseThrow(() -> new ResourceNotFoundException("Materia prima not found with id: " + materiaPrimaId));
            
            ProductMateriaPrima productMateriaPrima = new ProductMateriaPrima();
            productMateriaPrima.setProduct(product);
            productMateriaPrima.setMateriaPrima(materiaPrima);
            productMateriaPrima.setQuantityRequired(dto.getQuantityRequired());
            
            product.getProductMateriaPrimas().add(productMateriaPrima);
        }
    }
    
    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setCode(product.getCode());
        dto.setValue(product.getValue());
        
        if (product.getProductMateriaPrimas() != null) {
            List<ProductMateriaPrimaDTO> materiasPrimasDTO = product.getProductMateriaPrimas().stream()
                    .map(prm -> {
                        ProductMateriaPrimaDTO rmDTO = new ProductMateriaPrimaDTO();
                        rmDTO.setId(prm.getId());
                        rmDTO.setMateriaPrimaId(prm.getMateriaPrima().getId());
                        rmDTO.setMateriaPrimaName(prm.getMateriaPrima().getName());
                        rmDTO.setQuantityRequired(prm.getQuantityRequired());
                        return rmDTO;
                    })
                    .collect(Collectors.toList());
            dto.setMateriasPrimas(materiasPrimasDTO);
        }
        
        return dto;
    }

    private String generateProductCode(Long id) {
        return String.format("PRD-%06d", id);
    }

    private String generateTemporaryCode() {
        return "TMP-" + java.util.UUID.randomUUID();
    }
}

