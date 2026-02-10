package com.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_materias_primas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductMateriaPrima {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_prima_id", nullable = false)
    private MateriaPrima materiaPrima;
    
    @NotNull(message = "Quantity required is mandatory")
    @Positive(message = "Quantity required must be positive")
    @Column(nullable = false)
    private Integer quantityRequired;
}

