package com.example.WebApplicationDesign.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

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
    @JoinColumn(name = "series_id", nullable = true)
    @JsonIgnoreProperties({"films", "hibernateLazyInitializer", "handler"})
    Series series;

    @OneToMany(mappedBy = "film", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"film", "hibernateLazyInitializer", "handler"})
    private List<FilmsRating> filmsRatings = new ArrayList<>();
}
