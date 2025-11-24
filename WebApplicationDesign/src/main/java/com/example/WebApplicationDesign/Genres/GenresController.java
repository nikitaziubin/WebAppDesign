package com.example.WebApplicationDesign.Genres;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/genres")
@AllArgsConstructor
public class GenresController {
    private final GenresService genresService;

    @GetMapping
    public ResponseEntity<List<Genre>> getGenres() {
        return ResponseEntity.ok(genresService.getAllGenres());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable int id) {
        return ResponseEntity.ok(genresService.getGenreById(id));
    }
}
