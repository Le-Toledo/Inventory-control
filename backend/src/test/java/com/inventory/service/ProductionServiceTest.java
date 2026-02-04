package com.inventory.service;

import com.inventory.dto.ProductionReportDTO;
import com.inventory.entity.Product;
import com.inventory.entity.ProductRawMaterial;
import com.inventory.entity.RawMaterial;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductionServiceTest {
    
    @Mock
    private ProductRepository productRepository;
    
    @Mock
    private RawMaterialRepository rawMaterialRepository;
    
    @InjectMocks
    private ProductionService productionService;
    
    @Test
    void testCalculateProduction() {
        // Setup raw materials
        RawMaterial steel = new RawMaterial();
        steel.setId(1L);
        steel.setName("Steel");
        steel.setStockQuantity(100);
        
        RawMaterial aluminum = new RawMaterial();
        aluminum.setId(2L);
        aluminum.setName("Aluminum");
        aluminum.setStockQuantity(50);
        
        // Setup product
        Product product = new Product();
        product.setId(1L);
        product.setName("Chair");
        product.setValue(new BigDecimal("150.00"));
        product.setProductRawMaterials(new ArrayList<>());
        
        ProductRawMaterial prm1 = new ProductRawMaterial();
        prm1.setProduct(product);
        prm1.setRawMaterial(steel);
        prm1.setQuantityRequired(10);
        
        ProductRawMaterial prm2 = new ProductRawMaterial();
        prm2.setProduct(product);
        prm2.setRawMaterial(aluminum);
        prm2.setQuantityRequired(5);
        
        product.getProductRawMaterials().add(prm1);
        product.getProductRawMaterials().add(prm2);
        
        when(productRepository.findAllWithRawMaterials()).thenReturn(Arrays.asList(product));
        when(rawMaterialRepository.findAll()).thenReturn(Arrays.asList(steel, aluminum));
        
        ProductionReportDTO result = productionService.calculateProduction();
        
        assertNotNull(result);
        assertNotNull(result.getSuggestions());
        assertTrue(result.getSuggestions().size() > 0);
        assertEquals(new BigDecimal("1500.00"), result.getTotalValue());
    }
    
    @Test
    void testCalculateProduction_NoStock() {
        RawMaterial steel = new RawMaterial();
        steel.setId(1L);
        steel.setName("Steel");
        steel.setStockQuantity(0);
        
        Product product = new Product();
        product.setId(1L);
        product.setName("Chair");
        product.setValue(new BigDecimal("150.00"));
        product.setProductRawMaterials(new ArrayList<>());
        
        ProductRawMaterial prm = new ProductRawMaterial();
        prm.setProduct(product);
        prm.setRawMaterial(steel);
        prm.setQuantityRequired(10);
        product.getProductRawMaterials().add(prm);
        
        when(productRepository.findAllWithRawMaterials()).thenReturn(Arrays.asList(product));
        when(rawMaterialRepository.findAll()).thenReturn(Arrays.asList(steel));
        
        ProductionReportDTO result = productionService.calculateProduction();
        
        assertNotNull(result);
        assertEquals(0, result.getSuggestions().size());
        assertEquals(BigDecimal.ZERO, result.getTotalValue());
    }
}
