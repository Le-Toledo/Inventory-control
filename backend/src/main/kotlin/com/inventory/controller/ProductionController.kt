package com.inventory.controller

import com.inventory.dto.ProductionReportDTO
import com.inventory.service.ProductionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/production")
@CrossOrigin(origins = ["http://localhost:3000"])
class ProductionController(
    private val productionService: ProductionService
) {
    
    @GetMapping("/calculate")
    fun calculateProduction(): ResponseEntity<ProductionReportDTO> {
        val report = productionService.calculateProduction()
        return ResponseEntity.ok(report)
    }
}
