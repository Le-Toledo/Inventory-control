package com.inventory.repository;

import com.inventory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.productRawMaterials prm LEFT JOIN FETCH prm.rawMaterial")
    List<Product> findAllWithRawMaterials();
    
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.productRawMaterials prm LEFT JOIN FETCH prm.rawMaterial WHERE p.id = :id")
    Product findByIdWithRawMaterials(Long id);
}
