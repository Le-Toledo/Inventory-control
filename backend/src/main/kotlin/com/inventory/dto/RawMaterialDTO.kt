package com.inventory.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero

data class RawMaterialDTO(
    var id: Long? = null,
    
    @field:NotBlank(message = "Raw material name is required")
    var name: String = "",
    
    @field:NotNull(message = "Stock quantity is required")
    @field:PositiveOrZero(message = "Stock quantity must be zero or positive")
    var stockQuantity: Int = 0
)
