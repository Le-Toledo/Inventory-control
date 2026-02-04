package com.inventory.controller

import com.inventory.dto.RawMaterialDTO
import com.inventory.service.RawMaterialService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/raw-materials")
@CrossOrigin(origins = ["http://localhost:3000"])
class RawMaterialController(
    private val rawMaterialService: RawMaterialService
) {
    
    @GetMapping
    fun getAllRawMaterials(): ResponseEntity<List<RawMaterialDTO>> {
        val rawMaterials = rawMaterialService.findAll()
        return ResponseEntity.ok(rawMaterials)
    }
    
    @GetMapping("/{id}")
    fun getRawMaterialById(@PathVariable id: Long): ResponseEntity<RawMaterialDTO> {
        val rawMaterial = rawMaterialService.findById(id)
        return ResponseEntity.ok(rawMaterial)
    }
    
    @PostMapping
    fun createRawMaterial(@Valid @RequestBody rawMaterialDTO: RawMaterialDTO): ResponseEntity<RawMaterialDTO> {
        val createdRawMaterial = rawMaterialService.create(rawMaterialDTO)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRawMaterial)
    }
    
    @PutMapping("/{id}")
    fun updateRawMaterial(
        @PathVariable id: Long,
        @Valid @RequestBody rawMaterialDTO: RawMaterialDTO
    ): ResponseEntity<RawMaterialDTO> {
        val updatedRawMaterial = rawMaterialService.update(id, rawMaterialDTO)
        return ResponseEntity.ok(updatedRawMaterial)
    }
    
    @DeleteMapping("/{id}")
    fun deleteRawMaterial(@PathVariable id: Long): ResponseEntity<Void> {
        rawMaterialService.delete(id)
        return ResponseEntity.noContent().build()
    }
}
