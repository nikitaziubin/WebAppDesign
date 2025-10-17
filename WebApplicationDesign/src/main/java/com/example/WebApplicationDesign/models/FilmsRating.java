package com.example.WebApplicationDesign.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "FilmsRatings")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FilmsRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_of_publish", nullable = false)
    @CreationTimestamp
    private Date dateOfPublish = new Date();

    @Column(nullable = false)
    @NotNull private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false)
    @JsonIgnoreProperties({"filmsRatings", "hibernateLazyInitializer", "handler"})
    @NotNull private Film film;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"filmsRatings", "password", "hibernateLazyInitializer", "handler"})
    @NotNull private User user;
}