package com.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MateriaPrimaDTO {
    private Long id;
    
    @NotBlank(message = "Materia prima name is required")
    private String name;
    
    @NotNull(message = "Stock quantity is required")
    @PositiveOrZero(message = "Stock quantity must be zero or positive")
    private Integer stockQuantity;

    @NotNull(message = "Unit cost is required")
    @Positive(message = "Unit cost must be positive")
    private BigDecimal unitCost;

    @NotBlank(message = "Unit is required")
    private String unit;
}

