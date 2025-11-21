package com.example.WebApplicationDesign.Trailers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/trailers")
@AllArgsConstructor
public class TrailersController {
    TrailersService trailersService;

    @GetMapping
    public ResponseEntity<List<Trailer>> getTrailers() {
        return ResponseEntity.ok(trailersService.getTrailers());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Trailer> getTrailerById( @PathVariable int id){
        return ResponseEntity.ok(trailersService.getTrailer(id));
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Trailer> addTrailer(@RequestBody @Valid Trailer trailer){
        Trailer createdTrailer = trailersService.createTrailer(trailer);
        return ResponseEntity.created(URI.create("/api/trailers/" + createdTrailer.getId()))
                .body(createdTrailer);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Trailer> updateTrailer(@PathVariable int id, @RequestBody @Valid Trailer trailer) {
        Trailer updatedTrailer = trailersService.updateTrailer(id, trailer);
        return ResponseEntity.ok(updatedTrailer);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrailer(@PathVariable int id) {
        trailersService.deleteTrailer(id);
        return ResponseEntity.noContent().build();
    }
}
