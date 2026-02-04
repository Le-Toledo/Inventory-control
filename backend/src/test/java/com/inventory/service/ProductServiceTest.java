package com.inventory.service;

import com.inventory.dto.ProductDTO;
import com.inventory.entity.Product;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.repository.ProductRawMaterialRepository;
import com.inventory.repository.ProductRepository;
import com.inventory.repository.RawMaterialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    
    @Mock
    private ProductRepository productRepository;
    
    @Mock
    private RawMaterialRepository rawMaterialRepository;
    
    @Mock
    private ProductRawMaterialRepository productRawMaterialRepository;
    
    @InjectMocks
    private ProductService productService;
    
    private Product product;
    
    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setValue(new BigDecimal("100.00"));
        product.setProductRawMaterials(new ArrayList<>());
    }
    
    @Test
    void testFindAll() {
        List<Product> products = Arrays.asList(product);
        when(productRepository.findAllWithRawMaterials()).thenReturn(products);
        
        List<ProductDTO> result = productService.findAll();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Product", result.get(0).getName());
        verify(productRepository, times(1)).findAllWithRawMaterials();
    }
    
    @Test
    void testFindById_Success() {
        when(productRepository.findByIdWithRawMaterials(1L)).thenReturn(product);
        
        ProductDTO result = productService.findById(1L);
        
        assertNotNull(result);
        assertEquals("Test Product", result.getName());
        assertEquals(new BigDecimal("100.00"), result.getValue());
    }
    
    @Test
    void testFindById_NotFound() {
        when(productRepository.findByIdWithRawMaterials(999L)).thenReturn(null);
        
        assertThrows(ResourceNotFoundException.class, () -> {
            productService.findById(999L);
        });
    }
    
    @Test
    void testCreate() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("New Product");
        productDTO.setValue(new BigDecimal("150.00"));
        
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productRepository.findByIdWithRawMaterials(any())).thenReturn(product);
        
        ProductDTO result = productService.create(productDTO);
        
        assertNotNull(result);
        verify(productRepository, times(1)).save(any(Product.class));
    }
    
    @Test
    void testUpdate_Success() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Updated Product");
        productDTO.setValue(new BigDecimal("200.00"));
        
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productRepository.findByIdWithRawMaterials(1L)).thenReturn(product);
        
        ProductDTO result = productService.update(1L, productDTO);
        
        assertNotNull(result);
        verify(productRepository, times(1)).save(any(Product.class));
    }
    
    @Test
    void testDelete_Success() {
        when(productRepository.existsById(1L)).thenReturn(true);
        
        productService.delete(1L);
        
        verify(productRepository, times(1)).deleteById(1L);
    }
    
    @Test
    void testDelete_NotFound() {
        when(productRepository.existsById(999L)).thenReturn(false);
        
        assertThrows(ResourceNotFoundException.class, () -> {
            productService.delete(999L);
        });
    }
}
