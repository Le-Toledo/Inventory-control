package com.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionReportDTO {
    private List<ProductionSuggestionDTO> suggestions;
    private BigDecimal totalValue;
}
