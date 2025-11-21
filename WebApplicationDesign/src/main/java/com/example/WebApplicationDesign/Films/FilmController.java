package com.example.WebApplicationDesign.Films;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/films")
@AllArgsConstructor
public class FilmController {
    private FilmService filmService;

    @GetMapping
    public ResponseEntity<List<Film>> getFilms() {
        return ResponseEntity.ok(filmService.getAllFilms());
    }

    @GetMapping("{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable int id) {
        return ResponseEntity.ok(filmService.getFilmById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Film> createFilm(@RequestBody @Valid Film film) {
        Film createdFilm = filmService.createFilm(film);
        return ResponseEntity.created(URI.create("/api/films/" + createdFilm.getId()))
                .body(createdFilm);
    }
    @PutMapping("{id}")
    public ResponseEntity<Film> updateFilm(@PathVariable int id, @RequestBody @Valid Film film) {
        Film updatedFilm = filmService.updateFilm(id, film);
        return ResponseEntity.ok(updatedFilm);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable int id) {
        filmService.deleteFilm(id);
        return ResponseEntity.noContent().build();
    }
}
