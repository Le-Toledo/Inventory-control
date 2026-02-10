package com.inventory.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductMateriaPrimaDTO {
    private Long id;
    
    @NotNull(message = "Materia prima ID is required")
    private Long materiaPrimaId;
    
    private String materiaPrimaName;
    
    @NotNull(message = "Quantity required is required")
    @Positive(message = "Quantity required must be positive")
    private Integer quantityRequired;
}

