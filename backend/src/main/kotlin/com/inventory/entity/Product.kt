package com.inventory.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    
    @field:NotBlank(message = "Product name is required")
    @Column(nullable = false)
    var name: String = "",
    
    @field:NotNull(message = "Product value is required")
    @field:Positive(message = "Product value must be positive")
    @Column(name = "product_value", nullable = false, precision = 10, scale = 2)
    var value: BigDecimal = BigDecimal.ZERO,
    
    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], orphanRemoval = true)
    var productRawMaterials: MutableList<ProductRawMaterial> = mutableListOf()
) {
    fun addRawMaterial(rawMaterial: RawMaterial, quantity: Int) {
        val productRawMaterial = ProductRawMaterial(
            product = this,
            rawMaterial = rawMaterial,
            quantityRequired = quantity
        )
        productRawMaterials.add(productRawMaterial)
    }
    
    fun removeRawMaterial(productRawMaterial: ProductRawMaterial) {
        productRawMaterials.remove(productRawMaterial)
        productRawMaterial.product = null
    }
}
