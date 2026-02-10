package com.inventory.repository;

import com.inventory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.productMateriaPrimas prm LEFT JOIN FETCH prm.materiaPrima")
    List<Product> findAllWithMateriaPrimas();
    
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.productMateriaPrimas prm LEFT JOIN FETCH prm.materiaPrima WHERE p.id = :id")
    Product findByIdWithMateriaPrimas(Long id);
}

