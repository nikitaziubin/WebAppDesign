package com.example.WebApplicationDesign.ProductionCompanies;

import com.example.WebApplicationDesign.Films.Film;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
public class ProductionCompanies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    @NotNull private String name;
    @Column(nullable = false)
    @NotNull private String country;
    @NotNull private Date foundationDate;
    @NotNull private String contactEmail;

    @ToString.Exclude
    @OneToMany(mappedBy = "productionCompany", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"productionCompany", "filmsRatings", "filmsComments", "trailers", "hibernateLazyInitializer", "handler"})
    private List<Film> films = new ArrayList<>();
}
