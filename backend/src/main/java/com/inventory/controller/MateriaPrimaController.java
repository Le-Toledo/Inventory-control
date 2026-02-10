package com.inventory.controller;

import com.inventory.dto.MateriaPrimaDTO;
import com.inventory.service.MateriaPrimaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materias-primas")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class MateriaPrimaController {
    
    private final MateriaPrimaService materiaPrimaService;
    
    @GetMapping
    public ResponseEntity<List<MateriaPrimaDTO>> getAllMateriasPrimas() {
        List<MateriaPrimaDTO> materiasPrimas = materiaPrimaService.findAll();
        return ResponseEntity.ok(materiasPrimas);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MateriaPrimaDTO> getMateriaPrimaById(@PathVariable Long id) {
        MateriaPrimaDTO materiaPrima = materiaPrimaService.findById(id);
        return ResponseEntity.ok(materiaPrima);
    }
    
    @PostMapping
    public ResponseEntity<MateriaPrimaDTO> createMateriaPrima(@Valid @RequestBody MateriaPrimaDTO materiaPrimaDTO) {
        MateriaPrimaDTO createdMateriaPrima = materiaPrimaService.create(materiaPrimaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMateriaPrima);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<MateriaPrimaDTO> updateMateriaPrima(@PathVariable Long id, @Valid @RequestBody MateriaPrimaDTO materiaPrimaDTO) {
        MateriaPrimaDTO updatedMateriaPrima = materiaPrimaService.update(id, materiaPrimaDTO);
        return ResponseEntity.ok(updatedMateriaPrima);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMateriaPrima(@PathVariable Long id) {
        materiaPrimaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

