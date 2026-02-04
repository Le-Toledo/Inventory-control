package com.inventory.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

@Entity
@Table(name = "product_raw_materials")
data class ProductRawMaterial(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    var product: Product? = null,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "raw_material_id", nullable = false)
    var rawMaterial: RawMaterial? = null,
    
    @field:NotNull(message = "Quantity required is mandatory")
    @field:Positive(message = "Quantity required must be positive")
    @Column(nullable = false)
    var quantityRequired: Int = 0
)
