package com.example.WebApplicationDesign.Descriptions;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/wiki-descriptions")
@AllArgsConstructor
public class WikiDescriptionController {
    private final WikiDescriptionService descriptionService;

    @GetMapping
    public ResponseEntity<List<WikiDescription>> getWikiDescriptions() {
        return ResponseEntity.ok(descriptionService.getAllWikiDescription());
    }
    @GetMapping("/by-id/{id}")
    public ResponseEntity<WikiDescription> getWikiDescriptionById(@PathVariable int id) {
        return ResponseEntity.ok(descriptionService.getWikiDescriptionById(id));
    }
    @GetMapping("/by-film/{filmId}")
    public ResponseEntity<WikiDescription> getWikiDescriptionByFilmId(@PathVariable int filmId) {
        return ResponseEntity.ok(descriptionService.getWikiDescriptionByFilmId(filmId));
    }
    @PostMapping("/by-film/{filmId}")
    public ResponseEntity<WikiDescription> createWikiDescriptionToFilmID(@PathVariable int filmId) {
        WikiDescription wikiDescription = descriptionService.createWikiDescriptionToFilmID(filmId);
        return ResponseEntity.created(URI.create("/api/wiki-descriptions/" + wikiDescription.getId()))
                .body(wikiDescription);
    }
}
