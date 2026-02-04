package com.inventory.repository

import com.inventory.entity.ProductRawMaterial
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRawMaterialRepository : JpaRepository<ProductRawMaterial, Long> {
    fun findByProductId(productId: Long): List<ProductRawMaterial>
    fun deleteByProductId(productId: Long)
}
