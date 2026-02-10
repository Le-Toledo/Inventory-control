package com.inventory.repository;

import com.inventory.entity.ProductMateriaPrima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMateriaPrimaRepository extends JpaRepository<ProductMateriaPrima, Long> {
    List<ProductMateriaPrima> findByProductId(Long productId);
    void deleteByProductId(Long productId);
}

