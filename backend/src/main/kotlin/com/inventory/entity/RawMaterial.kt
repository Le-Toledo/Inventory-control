package com.inventory.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero

@Entity
@Table(name = "raw_materials")
data class RawMaterial(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    
    @field:NotBlank(message = "Raw material name is required")
    @Column(nullable = false)
    var name: String = "",
    
    @field:NotNull(message = "Stock quantity is required")
    @field:PositiveOrZero(message = "Stock quantity must be zero or positive")
    @Column(nullable = false)
    var stockQuantity: Int = 0,
    
    @OneToMany(mappedBy = "rawMaterial", cascade = [CascadeType.ALL])
    var productRawMaterials: MutableList<ProductRawMaterial> = mutableListOf()
)
