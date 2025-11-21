package com.example.WebApplicationDesign.Trailers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.example.WebApplicationDesign.Films.Film;

@Entity
@Table(name = "Trailers")
@Data
public class Trailer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    @NotNull private String trailerUrl;
    @Column(nullable = false)
    @NotNull private String ageLimit;
    @NotNull private String duration;
    @NotNull private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false)
    @JsonIgnoreProperties({"trailers", "hibernateLazyInitializer", "handler"})
    @NotNull private Film film;

}
