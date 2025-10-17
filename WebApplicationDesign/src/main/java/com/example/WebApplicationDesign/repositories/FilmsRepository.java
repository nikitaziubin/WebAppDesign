package com.example.WebApplicationDesign.repositories;

import com.example.WebApplicationDesign.models.Film;
import org.springframework.data.repository.ListCrudRepository;

public interface FilmsRepository extends ListCrudRepository<Film, Integer> {
}
