package com.example.WebApplicationDesign.repositories;

import com.example.WebApplicationDesign.models.FilmsRatings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmsRatingsRepository extends ListCrudRepository<FilmsRatings, Integer> {
}
