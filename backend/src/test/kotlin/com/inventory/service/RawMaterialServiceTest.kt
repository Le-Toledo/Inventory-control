package com.inventory.service

import com.inventory.dto.RawMaterialDTO
import com.inventory.entity.RawMaterial
import com.inventory.repository.RawMaterialRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class RawMaterialServiceTest {

    @Mock
    private lateinit var rawMaterialRepository: RawMaterialRepository

    @InjectMocks
    private lateinit var rawMaterialService: RawMaterialService

    private lateinit var testRawMaterial: RawMaterial
    private lateinit var testDTO: RawMaterialDTO

    @BeforeEach
    fun setup() {
        testRawMaterial = RawMaterial(
            id = 1L,
            name = "Farinha",
            stockQuantity = 100
        )

        testDTO = RawMaterialDTO(
            id = 1L,
            name = "Farinha",
            stockQuantity = 100
        )
    }

    @Test
    fun `should find all raw materials`() {
        // Given
        val rawMaterials = listOf(testRawMaterial)
        `when`(rawMaterialRepository.findAll()).thenReturn(rawMaterials)

        // When
        val result = rawMaterialService.findAll()

        // Then
        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals("Farinha", result[0].name)
        verify(rawMaterialRepository).findAll()
    }

    @Test
    fun `should find raw material by id`() {
        // Given
        `when`(rawMaterialRepository.findById(1L)).thenReturn(Optional.of(testRawMaterial))

        // When
        val result = rawMaterialService.findById(1L)

        // Then
        assertNotNull(result)
        assertEquals(1L, result.id)
        assertEquals("Farinha", result.name)
        assertEquals(100, result.stockQuantity)
        verify(rawMaterialRepository).findById(1L)
    }

    @Test
    fun `should create new raw material`() {
        // Given
        val newRawMaterial = RawMaterial(
            name = "Açúcar",
            stockQuantity = 50
        )
        val savedRawMaterial = newRawMaterial.copy(id = 2L)
        
        `when`(rawMaterialRepository.save(any(RawMaterial::class.java)))
            .thenReturn(savedRawMaterial)

        // When
        val dto = RawMaterialDTO(name = "Açúcar", stockQuantity = 50)
        val result = rawMaterialService.create(dto)

        // Then
        assertNotNull(result)
        assertEquals(2L, result.id)
        assertEquals("Açúcar", result.name)
        assertEquals(50, result.stockQuantity)
        verify(rawMaterialRepository).save(any(RawMaterial::class.java))
    }

    @Test
    fun `should update existing raw material`() {
        // Given
        `when`(rawMaterialRepository.findById(1L)).thenReturn(Optional.of(testRawMaterial))
        `when`(rawMaterialRepository.save(any(RawMaterial::class.java)))
            .thenReturn(testRawMaterial.apply { stockQuantity = 150 })

        // When
        val updateDTO = RawMaterialDTO(id = 1L, name = "Farinha", stockQuantity = 150)
        val result = rawMaterialService.update(1L, updateDTO)

        // Then
        assertNotNull(result)
        assertEquals(1L, result.id)
        assertEquals(150, result.stockQuantity)
        verify(rawMaterialRepository).findById(1L)
        verify(rawMaterialRepository).save(any(RawMaterial::class.java))
    }

    @Test
    fun `should delete raw material`() {
        // Given
        `when`(rawMaterialRepository.existsById(1L)).thenReturn(true)

        // When
        rawMaterialService.delete(1L)

        // Then
        verify(rawMaterialRepository).existsById(1L)
        verify(rawMaterialRepository).deleteById(1L)
    }
}
