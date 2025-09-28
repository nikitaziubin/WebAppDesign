package com.example.WebApplicationDesign.controllers;

import com.example.WebApplicationDesign.models.FilmsRatings;
import com.example.WebApplicationDesign.services.FilmsRatingsService;
import jakarta.persistence.EntityResult;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ConditionalOnIssuerLocationJwtDecoder;
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
    public ResponseEntity<List<FilmsRatings>> getFilmsRatings() {
        return ResponseEntity.ok(filmsRatingsService.getFilmsRatings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmsRatings> getFilmsRatingsById(@PathVariable int id) {
        return ResponseEntity.ok(filmsRatingsService.getFilmsRatingById(id));
    }
    @PostMapping
    public ResponseEntity<FilmsRatings> addFilmsRatings(@RequestBody @Valid FilmsRatings filmsRatings) {
        FilmsRatings createdFR = filmsRatingsService.createFilmsRating(filmsRatings);
        return ResponseEntity
                .created(URI.create("/api/films-ratings" + createdFR.getId()))
                .body(createdFR);
    }
    @PutMapping("/{id}")
    public ResponseEntity<FilmsRatings> updateFilmsRatings(@PathVariable int id, @RequestBody @Valid FilmsRatings filmsRatings) {
        FilmsRatings updatedFR = filmsRatingsService.updateFilmsRating(id, filmsRatings);
        return ResponseEntity.ok(updatedFR);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<FilmsRatings> deleteFilmsRatings(@PathVariable int id) {
        filmsRatingsService.deleteFilmsRating(id);
        return ResponseEntity.noContent().build();
    }
}
