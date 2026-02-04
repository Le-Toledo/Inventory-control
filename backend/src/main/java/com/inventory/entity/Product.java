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
    
    @NotNull(message = "Product value is required")
    @Positive(message = "Product value must be positive")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal value;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductRawMaterial> productRawMaterials = new ArrayList<>();
    
    public void addRawMaterial(RawMaterial rawMaterial, Integer quantity) {
        ProductRawMaterial productRawMaterial = new ProductRawMaterial();
        productRawMaterial.setProduct(this);
        productRawMaterial.setRawMaterial(rawMaterial);
        productRawMaterial.setQuantityRequired(quantity);
        productRawMaterials.add(productRawMaterial);
    }
    
    public void removeRawMaterial(ProductRawMaterial productRawMaterial) {
        productRawMaterials.remove(productRawMaterial);
        productRawMaterial.setProduct(null);
    }
}
