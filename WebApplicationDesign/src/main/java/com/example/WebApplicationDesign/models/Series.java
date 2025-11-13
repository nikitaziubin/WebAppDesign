package com.example.WebApplicationDesign.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Series {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotBlank private String name;
    @Column(nullable = false)
    @NotBlank private String ageLimit;
    @Column(nullable = false)
    private Date dateOfPublish;
    @Column(nullable = false)
    @NotBlank private String countryOfProduction;
    @Column(nullable = false)
    @NotBlank private String productionCompanyName;
    @Column(nullable = false)
    @NotBlank private String status;
    @Column(nullable = false)
    @NotNull private Integer numberOfEpisodes;

    @ToString.Exclude
    @OneToMany(mappedBy = "series", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"series", "hibernateLazyInitializer", "handler"})
    private List<Film> films = new ArrayList<>();
}
