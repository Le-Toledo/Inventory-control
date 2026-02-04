package com.inventory.dto

import java.math.BigDecimal

data class ProductionReportDTO(
    var suggestions: List<ProductionSuggestionDTO> = emptyList(),
    var totalValue: BigDecimal = BigDecimal.ZERO
)
