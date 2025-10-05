package com.example.WebApplicationDesign.controllers;

import com.example.WebApplicationDesign.models.FilmsRating;
import com.example.WebApplicationDesign.services.FilmsRatingsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/films-ratings")
@AllArgsConstructor
public class FilmsRatingsController {

    FilmsRatingsService filmsRatingsService;
    @GetMapping
    public ResponseEntity<List<FilmsRating>> getFilmsRatings() {
        return ResponseEntity.ok(filmsRatingsService.getFilmsRatings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmsRating> getFilmsRatingsById(@PathVariable int id) {
        return ResponseEntity.ok(filmsRatingsService.getFilmsRatingById(id));
    }
    @PostMapping
    public ResponseEntity<FilmsRating> addFilmsRatings(@RequestBody @Valid FilmsRating filmsRating) {
        FilmsRating createdFR = filmsRatingsService.createFilmsRating(filmsRating);
        return ResponseEntity
                .created(URI.create("/api/films-ratings" + createdFR.getId()))
                .body(createdFR);
    }
    @PutMapping("/{id}")
    public ResponseEntity<FilmsRating> updateFilmsRatings(@PathVariable int id, @RequestBody @Valid FilmsRating filmsRating) {
        FilmsRating updatedFR = filmsRatingsService.updateFilmsRating(id, filmsRating);
        return ResponseEntity.ok(updatedFR);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<FilmsRating> deleteFilmsRatings(@PathVariable int id) {
        filmsRatingsService.deleteFilmsRating(id);
        return ResponseEntity.noContent().build();
    }
}
