package com.inventory.service;

import com.inventory.dto.RawMaterialDTO;
import com.inventory.entity.RawMaterial;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.repository.RawMaterialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RawMaterialServiceTest {
    
    @Mock
    private RawMaterialRepository rawMaterialRepository;
    
    @InjectMocks
    private RawMaterialService rawMaterialService;
    
    private RawMaterial rawMaterial;
    
    @BeforeEach
    void setUp() {
        rawMaterial = new RawMaterial();
        rawMaterial.setId(1L);
        rawMaterial.setName("Steel");
        rawMaterial.setStockQuantity(1000);
    }
    
    @Test
    void testFindAll() {
        List<RawMaterial> rawMaterials = Arrays.asList(rawMaterial);
        when(rawMaterialRepository.findAll()).thenReturn(rawMaterials);
        
        List<RawMaterialDTO> result = rawMaterialService.findAll();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Steel", result.get(0).getName());
        verify(rawMaterialRepository, times(1)).findAll();
    }
    
    @Test
    void testFindById_Success() {
        when(rawMaterialRepository.findById(1L)).thenReturn(Optional.of(rawMaterial));
        
        RawMaterialDTO result = rawMaterialService.findById(1L);
        
        assertNotNull(result);
        assertEquals("Steel", result.getName());
        assertEquals(1000, result.getStockQuantity());
    }
    
    @Test
    void testFindById_NotFound() {
        when(rawMaterialRepository.findById(999L)).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> {
            rawMaterialService.findById(999L);
        });
    }
    
    @Test
    void testCreate() {
        RawMaterialDTO rawMaterialDTO = new RawMaterialDTO();
        rawMaterialDTO.setName("Aluminum");
        rawMaterialDTO.setStockQuantity(500);
        
        when(rawMaterialRepository.save(any(RawMaterial.class))).thenReturn(rawMaterial);
        
        RawMaterialDTO result = rawMaterialService.create(rawMaterialDTO);
        
        assertNotNull(result);
        verify(rawMaterialRepository, times(1)).save(any(RawMaterial.class));
    }
    
    @Test
    void testUpdate_Success() {
        RawMaterialDTO rawMaterialDTO = new RawMaterialDTO();
        rawMaterialDTO.setName("Updated Steel");
        rawMaterialDTO.setStockQuantity(1500);
        
        when(rawMaterialRepository.findById(1L)).thenReturn(Optional.of(rawMaterial));
        when(rawMaterialRepository.save(any(RawMaterial.class))).thenReturn(rawMaterial);
        
        RawMaterialDTO result = rawMaterialService.update(1L, rawMaterialDTO);
        
        assertNotNull(result);
        verify(rawMaterialRepository, times(1)).save(any(RawMaterial.class));
    }
    
    @Test
    void testDelete_Success() {
        when(rawMaterialRepository.existsById(1L)).thenReturn(true);
        
        rawMaterialService.delete(1L);
        
        verify(rawMaterialRepository, times(1)).deleteById(1L);
    }
    
    @Test
    void testDelete_NotFound() {
        when(rawMaterialRepository.existsById(999L)).thenReturn(false);
        
        assertThrows(ResourceNotFoundException.class, () -> {
            rawMaterialService.delete(999L);
        });
    }
}
