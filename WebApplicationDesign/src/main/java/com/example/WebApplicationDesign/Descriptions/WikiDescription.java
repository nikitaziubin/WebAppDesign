package com.example.WebApplicationDesign.Descriptions;

import com.example.WebApplicationDesign.Films.Film;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table
@Data
public class WikiDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length = 5000)
    private String descriptionText;

    @ToString.Exclude
    @OneToOne(mappedBy = "description")
    @JsonIgnoreProperties({"description", "hibernateLazyInitializer", "handler"})
    private Film film;
}
