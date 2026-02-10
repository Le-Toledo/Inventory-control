package com.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionSuggestionDTO {
    private Long productId;
    private String productName;
    private Integer quantityCanProduce;
    private BigDecimal productValue;
    private BigDecimal totalValue;
    private BigDecimal unitCost;
    private BigDecimal unitProfit;
    private BigDecimal totalCost;
    private BigDecimal profit;
    private BigDecimal profitMargin;
}
