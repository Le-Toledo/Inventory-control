package com.inventory.controller;

import com.inventory.dto.ProductionReportDTO;
import com.inventory.service.ProductionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/production")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ProductionController {
    
    private final ProductionService productionService;
    
    @GetMapping("/calculate")
    public ResponseEntity<ProductionReportDTO> calculateProduction() {
        ProductionReportDTO report = productionService.calculateProduction();
        return ResponseEntity.ok(report);
    }

    @PostMapping("/consume")
    public ResponseEntity<ProductionReportDTO> consumeProduction() {
        ProductionReportDTO report = productionService.consumeProduction();
        return ResponseEntity.ok(report);
    }
}
