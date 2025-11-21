package com.example.WebApplicationDesign.FilmComments;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/films-comments")
public class FilmsCommentsController {
    FilmsCommentsService filmsCommentsService;

    @GetMapping
    public ResponseEntity<List<FilmsComment>> getFilmsCommentsService() {
        return ResponseEntity.ok(filmsCommentsService.getFilmsComments());
    }
    @GetMapping("/{id}")
    public ResponseEntity<FilmsComment> getFilmsCommentById( @PathVariable int id) {
        return ResponseEntity.ok(filmsCommentsService.getFilmsComment(id));
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FilmsComment> addFilmsComment(@RequestBody FilmsComment filmsComment) {
        FilmsComment createdFC = filmsCommentsService.createFilmsComment(filmsComment);
        return ResponseEntity.created(URI.create("/api/films-comments/" + createdFC.getId()))
                .body(createdFC);
    }
    @PutMapping("/{id}")
    public ResponseEntity<FilmsComment> updateFilmsComment(@PathVariable int id, @RequestBody @Valid FilmsComment filmsComment) {
        FilmsComment updatedFC = filmsCommentsService.updateFilmsComment(id, filmsComment);
        return ResponseEntity.ok(updatedFC);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilmsComment(@PathVariable int id) {
        filmsCommentsService.deleteFilmsComment(id);
        return ResponseEntity.noContent().build();
    }

}
