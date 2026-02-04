package com.inventory.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class ProductDTO(
    var id: Long? = null,
    
    @field:NotBlank(message = "Product name is required")
    var name: String = "",
    
    @field:NotNull(message = "Product value is required")
    @field:Positive(message = "Product value must be positive")
    var value: BigDecimal = BigDecimal.ZERO,
    
    var rawMaterials: List<ProductRawMaterialDTO>? = null
)
