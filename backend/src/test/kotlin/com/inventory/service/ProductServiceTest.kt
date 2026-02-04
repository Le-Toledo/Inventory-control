package com.inventory.service

import com.inventory.dto.ProductDTO
import com.inventory.entity.Product
import com.inventory.exception.ResourceNotFoundException
import com.inventory.repository.ProductRawMaterialRepository
import com.inventory.repository.ProductRepository
import com.inventory.repository.RawMaterialRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal
import java.util.*

@ExtendWith(MockitoExtension::class)
class ProductServiceTest {
    
    @Mock
    private lateinit var productRepository: ProductRepository
    
    @Mock
    private lateinit var rawMaterialRepository: RawMaterialRepository
    
    @Mock
    private lateinit var productRawMaterialRepository: ProductRawMaterialRepository
    
    @InjectMocks
    private lateinit var productService: ProductService
    
    private lateinit var product: Product
    
    @BeforeEach
    fun setUp() {
        product = Product(
            id = 1L,
            name = "Test Product",
            value = BigDecimal("100.00"),
            productRawMaterials = mutableListOf()
        )
    }
    
    @Test
    fun `should find all products`() {
        `when`(productRepository.findAllWithRawMaterials()).thenReturn(listOf(product))
        
        val result = productService.findAll()
        
        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals("Test Product", result[0].name)
        verify(productRepository, times(1)).findAllWithRawMaterials()
    }
    
    @Test
    fun `should find product by id successfully`() {
        `when`(productRepository.findByIdWithRawMaterials(1L)).thenReturn(product)
        
        val result = productService.findById(1L)
        
        assertNotNull(result)
        assertEquals("Test Product", result.name)
        assertEquals(BigDecimal("100.00"), result.value)
    }
    
    @Test
    fun `should throw exception when product not found`() {
        `when`(productRepository.findByIdWithRawMaterials(999L)).thenReturn(null)
        
        assertThrows<ResourceNotFoundException> {
            productService.findById(999L)
        }
    }
    
    @Test
    fun `should create product`() {
        val productDTO = ProductDTO(
            name = "New Product",
            value = BigDecimal("150.00")
        )
        
        `when`(productRepository.save(any(Product::class.java))).thenReturn(product)
        `when`(productRepository.findByIdWithRawMaterials(any())).thenReturn(product)
        
        val result = productService.create(productDTO)
        
        assertNotNull(result)
        verify(productRepository, times(1)).save(any(Product::class.java))
    }
    
    @Test
    fun `should delete product successfully`() {
        `when`(productRepository.existsById(1L)).thenReturn(true)
        
        productService.delete(1L)
        
        verify(productRepository, times(1)).deleteById(1L)
    }
    
    @Test
    fun `should throw exception when deleting non-existent product`() {
        `when`(productRepository.existsById(999L)).thenReturn(false)
        
        assertThrows<ResourceNotFoundException> {
            productService.delete(999L)
        }
    }
}
