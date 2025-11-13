package com.example.WebApplicationDesign.repositories;

import com.example.WebApplicationDesign.models.Film;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface FilmsRepository extends ListCrudRepository<Film, Integer> {
    public List<Film> findFilmsBySeriesIsNull();
    public List<Film> findFilmsBySeriesId(int seriesId);
}
