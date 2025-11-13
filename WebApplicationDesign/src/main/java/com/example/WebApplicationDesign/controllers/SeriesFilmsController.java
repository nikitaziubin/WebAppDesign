package com.example.WebApplicationDesign.controllers;

import com.example.WebApplicationDesign.models.Film;
import com.example.WebApplicationDesign.services.FilmService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/series/{seriesId}/films")
@AllArgsConstructor
public class SeriesFilmsController {
    private final FilmService filmService;

    @GetMapping
    public ResponseEntity<List<Film>> getFilmsForSeries(@PathVariable int seriesId) {
        return ResponseEntity.ok(filmService.getFilmsBySeries(seriesId));
    }

    @GetMapping("/{filmId}")
    public ResponseEntity<Film> getFilmInSeries(@PathVariable int seriesId, @PathVariable int filmId) {
        return ResponseEntity.ok(filmService.getFilmInSeries(seriesId, filmId));
    }

}