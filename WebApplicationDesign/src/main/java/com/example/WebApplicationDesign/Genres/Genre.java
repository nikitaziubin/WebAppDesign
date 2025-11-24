package com.example.WebApplicationDesign.Genres;

import com.example.WebApplicationDesign.Films.Film;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    @NotNull private String genreName;
    private String description;
    private Integer popularityScore;

    @ToString.Exclude
    @ManyToMany(mappedBy = "genres")
    @JsonIgnoreProperties({
            "genres",
            "productionCompany",
            "filmsRatings",
            "filmsComments",
            "trailers",
            "series",
            "hibernateLazyInitializer",
            "handler"
    })
    private List<Film> films = new ArrayList<>();

}
