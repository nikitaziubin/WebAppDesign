package com.example.WebApplicationDesign.Films;

import com.example.WebApplicationDesign.FilmComments.FilmsComment;
import com.example.WebApplicationDesign.FilmRatings.FilmsRating;
import com.example.WebApplicationDesign.Genres.Genre;
import com.example.WebApplicationDesign.ProductionCompanies.ProductionCompanies;
import com.example.WebApplicationDesign.Series.Series;
import com.example.WebApplicationDesign.Trailers.Trailer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Data
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotBlank private String name;

    @Column(nullable = false)
    @NotBlank private String quality;

    @Column(nullable = false)
    @NotBlank private String duration;

    @NotBlank private String previewPhoto;

    @NotBlank private String ageLimit;

    @Column(nullable = false)
    private Date dateOfPublish;

    @Column(nullable = false)
    @NotBlank private String budget;

    @Column(nullable = false)
    @NotBlank private String language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_id")
    @JsonIgnoreProperties({"films", "hibernateLazyInitializer", "handler"})
    Series series;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_company_id")
    @JsonIgnoreProperties({"films", "hibernateLazyInitializer", "handler"})
    ProductionCompanies productionCompany;

    @ToString.Exclude
    @OneToMany(mappedBy = "film", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"film", "hibernateLazyInitializer", "handler"})
    private List<FilmsRating> filmsRatings = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "film", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"film", "hibernateLazyInitializer", "handler"})
    private List<FilmsComment> filmsComments = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "film", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"film", "hibernateLazyInitializer", "handler"})
    private List<Trailer> trailers = new ArrayList<>();


    @ManyToMany
    @JoinTable(
            name = "film_genre",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"),
            uniqueConstraints = @UniqueConstraint(
                    name = "unique_film_genre",
                    columnNames = {"film_id", "genre_id"}
            )
    )
    @JsonIgnoreProperties({"films", "hibernateLazyInitializer", "handler"})
    private List<Genre> genres = new ArrayList<>();
}
