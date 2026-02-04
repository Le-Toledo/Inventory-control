package com.inventory.service

import com.inventory.dto.RawMaterialDTO
import com.inventory.entity.RawMaterial
import com.inventory.exception.ResourceNotFoundException
import com.inventory.repository.RawMaterialRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RawMaterialService(
    private val rawMaterialRepository: RawMaterialRepository
) {
    
    @Transactional(readOnly = true)
    fun findAll(): List<RawMaterialDTO> {
        return rawMaterialRepository.findAll().map { convertToDTO(it) }
    }
    
    @Transactional(readOnly = true)
    fun findById(id: Long): RawMaterialDTO {
        val rawMaterial = rawMaterialRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Raw material not found with id: $id") }
        return convertToDTO(rawMaterial)
    }
    
    @Transactional
    fun create(rawMaterialDTO: RawMaterialDTO): RawMaterialDTO {
        val rawMaterial = RawMaterial(
            name = rawMaterialDTO.name,
            stockQuantity = rawMaterialDTO.stockQuantity
        )
        
        val savedRawMaterial = rawMaterialRepository.save(rawMaterial)
        return convertToDTO(savedRawMaterial)
    }
    
    @Transactional
    fun update(id: Long, rawMaterialDTO: RawMaterialDTO): RawMaterialDTO {
        val rawMaterial = rawMaterialRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Raw material not found with id: $id") }
        
        rawMaterial.name = rawMaterialDTO.name
        rawMaterial.stockQuantity = rawMaterialDTO.stockQuantity
        
        val updatedRawMaterial = rawMaterialRepository.save(rawMaterial)
        return convertToDTO(updatedRawMaterial)
    }
    
    @Transactional
    fun delete(id: Long) {
        if (!rawMaterialRepository.existsById(id)) {
            throw ResourceNotFoundException("Raw material not found with id: $id")
        }
        rawMaterialRepository.deleteById(id)
    }
    
    private fun convertToDTO(rawMaterial: RawMaterial): RawMaterialDTO {
        return RawMaterialDTO(
            id = rawMaterial.id,
            name = rawMaterial.name,
            stockQuantity = rawMaterial.stockQuantity
        )
    }
}
