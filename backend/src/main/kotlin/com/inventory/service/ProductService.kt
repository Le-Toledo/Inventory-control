package com.inventory.service

import com.inventory.dto.ProductDTO
import com.inventory.dto.ProductRawMaterialDTO
import com.inventory.entity.Product
import com.inventory.entity.ProductRawMaterial
import com.inventory.exception.ResourceNotFoundException
import com.inventory.repository.ProductRawMaterialRepository
import com.inventory.repository.ProductRepository
import com.inventory.repository.RawMaterialRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val rawMaterialRepository: RawMaterialRepository,
    private val productRawMaterialRepository: ProductRawMaterialRepository
) {
    
    @Transactional(readOnly = true)
    fun findAll(): List<ProductDTO> {
        return productRepository.findAllWithRawMaterials().map { convertToDTO(it) }
    }
    
    @Transactional(readOnly = true)
    fun findById(id: Long): ProductDTO {
        val product = productRepository.findByIdWithRawMaterials(id)
            ?: throw ResourceNotFoundException("Product not found with id: $id")
        return convertToDTO(product)
    }
    
    @Transactional
    fun create(productDTO: ProductDTO): ProductDTO {
        val product = Product(
            name = productDTO.name,
            value = productDTO.value
        )
        
        val savedProduct = productRepository.save(product)
        
        productDTO.rawMaterials?.takeIf { it.isNotEmpty() }?.let {
            updateProductRawMaterials(savedProduct, it)
        }
        
        return convertToDTO(productRepository.findByIdWithRawMaterials(savedProduct.id!!)!!)
    }
    
    @Transactional
    fun update(id: Long, productDTO: ProductDTO): ProductDTO {
        val product = productRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Product not found with id: $id") }
        
        product.name = productDTO.name
        product.value = productDTO.value
        
        productDTO.rawMaterials?.let {
            productRawMaterialRepository.deleteByProductId(id)
            product.productRawMaterials.clear()
            updateProductRawMaterials(product, it)
        }
        
        productRepository.save(product)
        return convertToDTO(productRepository.findByIdWithRawMaterials(id)!!)
    }
    
    @Transactional
    fun delete(id: Long) {
        if (!productRepository.existsById(id)) {
            throw ResourceNotFoundException("Product not found with id: $id")
        }
        productRepository.deleteById(id)
    }
    
    private fun updateProductRawMaterials(product: Product, rawMaterialsDTO: List<ProductRawMaterialDTO>) {
        rawMaterialsDTO.forEach { dto ->
            val rawMaterial = rawMaterialRepository.findById(dto.rawMaterialId!!)
                .orElseThrow { ResourceNotFoundException("Raw material not found with id: ${dto.rawMaterialId}") }
            
            val productRawMaterial = ProductRawMaterial(
                product = product,
                rawMaterial = rawMaterial,
                quantityRequired = dto.quantityRequired
            )
            
            product.productRawMaterials.add(productRawMaterial)
        }
    }
    
    private fun convertToDTO(product: Product): ProductDTO {
        return ProductDTO(
            id = product.id,
            name = product.name,
            value = product.value,
            rawMaterials = product.productRawMaterials.map { prm ->
                ProductRawMaterialDTO(
                    id = prm.id,
                    rawMaterialId = prm.rawMaterial?.id,
                    rawMaterialName = prm.rawMaterial?.name,
                    quantityRequired = prm.quantityRequired
                )
            }
        )
    }
}
