package com.inventory.service;

import com.inventory.dto.MateriaPrimaDTO;
import com.inventory.entity.MateriaPrima;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.repository.MateriaPrimaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MateriaPrimaService {
    
    private final MateriaPrimaRepository materiaPrimaRepository;
    
    @Transactional(readOnly = true)
    public List<MateriaPrimaDTO> findAll() {
        return materiaPrimaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public MateriaPrimaDTO findById(Long id) {
        Long safeId = Objects.requireNonNull(id, "id is required");
        MateriaPrima materiaPrima = materiaPrimaRepository.findById(safeId)
            .orElseThrow(() -> new ResourceNotFoundException("Materia prima not found with id: " + safeId));
        return convertToDTO(materiaPrima);
    }
    
    @Transactional
    public MateriaPrimaDTO create(MateriaPrimaDTO materiaPrimaDTO) {
        MateriaPrima materiaPrima = new MateriaPrima();
        materiaPrima.setName(materiaPrimaDTO.getName());
        materiaPrima.setStockQuantity(materiaPrimaDTO.getStockQuantity());
        materiaPrima.setUnitCost(materiaPrimaDTO.getUnitCost());
        materiaPrima.setUnit(materiaPrimaDTO.getUnit());
        
        MateriaPrima savedMateriaPrima = materiaPrimaRepository.save(materiaPrima);
        return convertToDTO(savedMateriaPrima);
    }
    
    @Transactional
    public MateriaPrimaDTO update(Long id, MateriaPrimaDTO materiaPrimaDTO) {
        Long safeId = Objects.requireNonNull(id, "id is required");
        MateriaPrima materiaPrima = materiaPrimaRepository.findById(safeId)
            .orElseThrow(() -> new ResourceNotFoundException("Materia prima not found with id: " + safeId));
        
        materiaPrima.setName(materiaPrimaDTO.getName());
        materiaPrima.setStockQuantity(materiaPrimaDTO.getStockQuantity());
        materiaPrima.setUnitCost(materiaPrimaDTO.getUnitCost());
        materiaPrima.setUnit(materiaPrimaDTO.getUnit());
        
        MateriaPrima updatedMateriaPrima = materiaPrimaRepository.save(materiaPrima);
        return convertToDTO(updatedMateriaPrima);
    }
    
    @Transactional
    public void delete(Long id) {
        Long safeId = Objects.requireNonNull(id, "id is required");
        if (!materiaPrimaRepository.existsById(safeId)) {
            throw new ResourceNotFoundException("Materia prima not found with id: " + safeId);
        }
        materiaPrimaRepository.deleteById(safeId);
    }
    
    private MateriaPrimaDTO convertToDTO(MateriaPrima materiaPrima) {
        MateriaPrimaDTO dto = new MateriaPrimaDTO();
        dto.setId(materiaPrima.getId());
        dto.setName(materiaPrima.getName());
        dto.setStockQuantity(materiaPrima.getStockQuantity());
        dto.setUnitCost(materiaPrima.getUnitCost());
        dto.setUnit(materiaPrima.getUnit());
        return dto;
    }
}

