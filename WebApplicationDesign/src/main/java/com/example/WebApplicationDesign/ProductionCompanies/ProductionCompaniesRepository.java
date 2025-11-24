package com.example.WebApplicationDesign.ProductionCompanies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionCompaniesRepository extends ListCrudRepository<ProductionCompanies, Integer> {
}
