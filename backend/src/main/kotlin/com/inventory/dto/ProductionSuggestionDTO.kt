package com.inventory.dto

import java.math.BigDecimal

data class ProductionSuggestionDTO(
    var productId: Long? = null,
    var productName: String = "",
    var quantityCanProduce: Int = 0,
    var productValue: BigDecimal = BigDecimal.ZERO,
    var totalValue: BigDecimal = BigDecimal.ZERO
)
