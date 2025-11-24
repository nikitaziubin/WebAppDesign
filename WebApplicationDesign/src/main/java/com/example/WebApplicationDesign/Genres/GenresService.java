package com.example.WebApplicationDesign.Genres;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GenresService {
    private final GenresRepository genresRepository;

    public List<Genre> getAllGenres() {
        return genresRepository.findAll();
    }

    public Genre getGenreById(int id) {
        return genresRepository.findById(id).orElseThrow(() -> new RuntimeException("Genre not found by id: " + id));
    }
}
