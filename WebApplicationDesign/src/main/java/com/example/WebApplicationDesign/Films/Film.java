package com.example.WebApplicationDesign.Films;

import com.example.WebApplicationDesign.Descriptions.WikiDescription;
import com.example.WebApplicationDesign.FilmComments.FilmsComment;
import com.example.WebApplicationDesign.FilmRatings.FilmsRating;
import com.example.WebApplicationDesign.Genres.Genre;
import com.example.WebApplicationDesign.Payments.Payment;
import com.example.WebApplicationDesign.ProductionCompanies.ProductionCompanies;
import com.example.WebApplicationDesign.Series.Series;
import com.example.WebApplicationDesign.Trailers.Trailer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
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
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Quality cannot be blank")
    private String quality;

    @Column(nullable = false)
    @NotBlank(message = "Duration cannot be blank")
    private String duration;

    @NotBlank(message = "Preview photo must be a valid URL")
    private String previewPhoto;

    @NotBlank(message = "Age limit cannot be blank")
    private String ageLimit;

    @Column(nullable = false)
    private Date dateOfPublish;

    @Column(nullable = false)
    @Positive(message = "Budget must be positive")
    @NotNull private BigDecimal budget;

    @Column(nullable = false)
    @NotBlank(message = "Language cannot be blank")
    private String language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_id")
    @JsonIgnoreProperties({"films", "hibernateLazyInitializer", "handler"})
    Series series;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_company_id")
    @JsonIgnoreProperties({"films", "hibernateLazyInitializer", "handler"})
    @NotNull(message = "Production company must be specified")
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
    @ToString.Exclude
    @JsonIgnoreProperties({"films", "hibernateLazyInitializer", "handler"})
    @NotNull(message = "At least one genre must be specified")
    private List<Genre> genres = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "film", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"film", "hibernateLazyInitializer", "handler"})
    private List<Payment> payments = new ArrayList<>();

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "wiki_description_id", referencedColumnName = "id", nullable = true)
    @JsonIgnoreProperties({"film", "hibernateLazyInitializer", "handler"})
    private WikiDescription description;
}
