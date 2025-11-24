package com.example.WebApplicationDesign.Genres;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenresRepository extends ListCrudRepository<Genre, Integer> {
}
