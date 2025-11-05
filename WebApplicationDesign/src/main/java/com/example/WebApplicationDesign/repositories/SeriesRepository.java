package com.example.WebApplicationDesign.repositories;

import com.example.WebApplicationDesign.models.Series;
import org.springframework.data.repository.ListCrudRepository;

public interface SeriesRepository extends ListCrudRepository<Series, Integer> {
}
