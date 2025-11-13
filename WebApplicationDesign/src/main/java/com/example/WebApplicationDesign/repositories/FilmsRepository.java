package com.example.WebApplicationDesign.repositories;

import com.example.WebApplicationDesign.models.Film;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface FilmsRepository extends ListCrudRepository<Film, Integer> {
    List<Film> findFilmsBySeriesIsNull();
    List<Film> findFilmsBySeriesId(int seriesId);
}
