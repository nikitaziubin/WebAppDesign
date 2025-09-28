package com.example.WebApplicationDesign.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FilmsRatings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date_of_publish", nullable = false)
    private Date dateOfPublish = new Date();

    @Column(nullable = false)
    private int rating;

    @Column(name = "film_id", nullable = false)
    private int filmId;

    @Column(name = "user_id", insertable = false, updatable = false)
    private int userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"filmsRatings", "password", "hibernateLazyInitializer", "handler"})
    private Users user;
}