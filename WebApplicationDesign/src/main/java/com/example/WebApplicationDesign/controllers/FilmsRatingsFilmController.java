package com.example.WebApplicationDesign.controllers;

import com.example.WebApplicationDesign.models.FilmsRating;
import com.example.WebApplicationDesign.services.FilmsRatingsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/series/{seriesId}/films/{filmId}/films-ratings")
@AllArgsConstructor
public class FilmsRatingsFilmController {
    FilmsRatingsService filmsRatingsService;

    @GetMapping
    public ResponseEntity<List<FilmsRating>> getFilmsRatingsForFilm(@PathVariable int filmId, @PathVariable String seriesId) {
        return ResponseEntity.ok(filmsRatingsService.getFilmsRatingsByFilm(filmId));
    }

    @GetMapping("/{filmsRatingId}")
    public ResponseEntity<FilmsRating> getFilmsRatingsForFilm(@PathVariable int filmId, @PathVariable int filmsRatingId, @PathVariable String seriesId) {
        return ResponseEntity.ok(filmsRatingsService.getFilmsRatingForFilm(filmsRatingId));
    }
}
