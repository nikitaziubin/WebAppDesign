package com.example.WebApplicationDesign.controllers;

import com.example.WebApplicationDesign.models.Series;
import com.example.WebApplicationDesign.services.SeriesService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path ="/api/series")
@AllArgsConstructor
public class SeriesController {
    SeriesService seriesService;

    @GetMapping
    public ResponseEntity<List<Series>> getAllSeries() {
        return ResponseEntity.ok(seriesService.getAllSeries());
    }
    @GetMapping("{id}")
    public ResponseEntity<Series> getSeriesById(@PathVariable int id) {
        return ResponseEntity.ok(seriesService.getSeriesById(id));
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Series> createSeries(@RequestBody @Valid Series series) {
        Series createdSeries = seriesService.createSeries(series);
        return ResponseEntity.created(URI.create("/api/series/" + createdSeries.getId()))
                .body(createdSeries);
    }
    @PutMapping("{id}")
    public ResponseEntity<Series> updateSeries(@PathVariable int id, @RequestBody @Valid Series series) {
        Series updatedSeries = seriesService.updateSeries(series, id);
        return ResponseEntity.ok(updatedSeries);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSeries(@PathVariable int id) {
        seriesService.deleteSeries(id);
        return ResponseEntity.noContent().build();
    }
}
