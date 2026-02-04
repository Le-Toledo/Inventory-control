package com.inventory.service

import com.inventory.dto.ProductionReportDTO
import com.inventory.dto.ProductionSuggestionDTO
import com.inventory.entity.Product
import com.inventory.repository.ProductRepository
import com.inventory.repository.RawMaterialRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import kotlin.math.min

@Service
class ProductionService(
    private val productRepository: ProductRepository,
    private val rawMaterialRepository: RawMaterialRepository
) {
    
    @Transactional(readOnly = true)
    fun calculateProduction(): ProductionReportDTO {
        val products = productRepository.findAllWithRawMaterials()
            .sortedByDescending { it.value }
        
        val availableStock = rawMaterialRepository.findAll()
            .associate { it.id!! to it.stockQuantity }
            .toMutableMap()
        
        val suggestions = mutableListOf<ProductionSuggestionDTO>()
        var totalValue = BigDecimal.ZERO
        
        products.forEach productLoop@{ product ->
            if (product.productRawMaterials.isEmpty()) return@productLoop
            
            val maxQuantity = calculateMaxQuantity(product, availableStock)
            
            if (maxQuantity > 0) {
                product.productRawMaterials.forEach materialLoop@{ prm ->
                    val rawMaterialId = prm.rawMaterial?.id ?: return@materialLoop
                    val currentStock = availableStock[rawMaterialId] ?: 0
                    val newStock = currentStock - (prm.quantityRequired * maxQuantity)
                    availableStock[rawMaterialId] = newStock
                }
                
                val productTotalValue = product.value.multiply(BigDecimal(maxQuantity))
                totalValue = totalValue.add(productTotalValue)
                
                suggestions.add(
                    ProductionSuggestionDTO(
                        productId = product.id,
                        productName = product.name,
                        quantityCanProduce = maxQuantity,
                        productValue = product.value,
                        totalValue = productTotalValue
                    )
                )
            }
        }
        
        return ProductionReportDTO(
            suggestions = suggestions,
            totalValue = totalValue
        )
    }
    
    private fun calculateMaxQuantity(product: Product, availableStock: Map<Long, Int>): Int {
        var maxQuantity = Int.MAX_VALUE
        
        product.productRawMaterials.forEach { prm ->
            val rawMaterialId = prm.rawMaterial?.id ?: return@forEach
            val available = availableStock[rawMaterialId] ?: 0
            val required = prm.quantityRequired
            
            if (required == 0) return@forEach
            
            val possibleQuantity = available / required
            maxQuantity = min(maxQuantity, possibleQuantity)
        }
        
        return if (maxQuantity == Int.MAX_VALUE) 0 else maxQuantity
    }
}
