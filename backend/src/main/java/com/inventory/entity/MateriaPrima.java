package com.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

@Entity
@Table(name = "materias_primas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MateriaPrima {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Materia prima name is required")
    @Column(nullable = false)
    private String name;
    
    @NotNull(message = "Stock quantity is required")
    @PositiveOrZero(message = "Stock quantity must be zero or positive")
    @Column(nullable = false)
    private Integer stockQuantity;

    @NotNull(message = "Unit cost is required")
    @Positive(message = "Unit cost must be positive")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitCost;

    @NotBlank(message = "Unit is required")
    @Column(nullable = false)
    private String unit;
    
    @OneToMany(mappedBy = "materiaPrima", cascade = CascadeType.ALL)
    private List<ProductMateriaPrima> productMateriaPrimas = new ArrayList<>();
}

