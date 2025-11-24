package com.example.WebApplicationDesign.ProductionCompanies;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/production-companies")
@AllArgsConstructor
public class ProductionCompaniesController {
    private final ProductionCompaniesService productionCompaniesService;

    @GetMapping
    public ResponseEntity<List<ProductionCompanies>> getAllProductionCompanies() {
        return ResponseEntity.ok(productionCompaniesService.getAllProductionCompanies());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductionCompanies> getProductionCompanyById(@PathVariable int id) {
        return ResponseEntity.ok(productionCompaniesService.getProductionCompaniesById(id));
    }
}
