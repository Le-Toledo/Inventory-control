package com.inventory.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class ProductRawMaterialDTO(
    var id: Long? = null,
    
    @field:NotNull(message = "Raw material ID is required")
    var rawMaterialId: Long? = null,
    
    var rawMaterialName: String? = null,
    
    @field:NotNull(message = "Quantity required is required")
    @field:Positive(message = "Quantity required must be positive")
    var quantityRequired: Int = 0
)
