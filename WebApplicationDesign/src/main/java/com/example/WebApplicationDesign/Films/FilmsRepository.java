package com.example.WebApplicationDesign.Films;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface FilmsRepository extends ListCrudRepository<Film, Integer> {
    List<Film> findFilmsBySeriesIsNull();
    List<Film> findFilmsBySeriesId(int seriesId);
}
