package com.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Product name is required")
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;
    
    @NotNull(message = "Product value is required")
    @Positive(message = "Product value must be positive")
    @Column(name = "product_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal value;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductMateriaPrima> productMateriaPrimas = new ArrayList<>();
    
    public void addMateriaPrima(MateriaPrima materiaPrima, Integer quantity) {
        ProductMateriaPrima productMateriaPrima = new ProductMateriaPrima();
        productMateriaPrima.setProduct(this);
        productMateriaPrima.setMateriaPrima(materiaPrima);
        productMateriaPrima.setQuantityRequired(quantity);
        productMateriaPrimas.add(productMateriaPrima);
    }
    
    public void removeMateriaPrima(ProductMateriaPrima productMateriaPrima) {
        productMateriaPrimas.remove(productMateriaPrima);
        productMateriaPrima.setProduct(null);
    }
}

