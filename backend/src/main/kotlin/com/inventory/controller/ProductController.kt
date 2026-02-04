package com.inventory.controller

import com.inventory.dto.ProductDTO
import com.inventory.service.ProductService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = ["http://localhost:3000"])
class ProductController(
    private val productService: ProductService
) {
    
    @GetMapping
    fun getAllProducts(): ResponseEntity<List<ProductDTO>> {
        val products = productService.findAll()
        return ResponseEntity.ok(products)
    }
    
    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long): ResponseEntity<ProductDTO> {
        val product = productService.findById(id)
        return ResponseEntity.ok(product)
    }
    
    @PostMapping
    fun createProduct(@Valid @RequestBody productDTO: ProductDTO): ResponseEntity<ProductDTO> {
        val createdProduct = productService.create(productDTO)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct)
    }
    
    @PutMapping("/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @Valid @RequestBody productDTO: ProductDTO
    ): ResponseEntity<ProductDTO> {
        val updatedProduct = productService.update(id, productDTO)
        return ResponseEntity.ok(updatedProduct)
    }
    
    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Void> {
        productService.delete(id)
        return ResponseEntity.noContent().build()
    }
}
