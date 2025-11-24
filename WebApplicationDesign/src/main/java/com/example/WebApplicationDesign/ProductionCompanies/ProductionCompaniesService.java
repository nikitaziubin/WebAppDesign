package com.example.WebApplicationDesign.ProductionCompanies;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductionCompaniesService {
    private final ProductionCompaniesRepository productionCompaniesRepository;

    public List<ProductionCompanies> getAllProductionCompanies() {
        return productionCompaniesRepository.findAll();
    }
    public ProductionCompanies getProductionCompaniesById(int id) {
        return productionCompaniesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Production Company not found by id: " + id));
    }
}
