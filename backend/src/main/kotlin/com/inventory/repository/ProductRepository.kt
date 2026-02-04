package com.inventory.repository

import com.inventory.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.productRawMaterials prm LEFT JOIN FETCH prm.rawMaterial")
    fun findAllWithRawMaterials(): List<Product>
    
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.productRawMaterials prm LEFT JOIN FETCH prm.rawMaterial WHERE p.id = :id")
    fun findByIdWithRawMaterials(id: Long): Product?
}
