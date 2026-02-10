package com.inventory.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRawMaterialDTO {
    private Long id;
    
    @NotNull(message = "Raw material ID is required")
    private Long rawMaterialId;
    
    private String rawMaterialName;
    
    @NotNull(message = "Quantity required is required")
    @Positive(message = "Quantity required must be positive")
    private Integer quantityRequired;
}
