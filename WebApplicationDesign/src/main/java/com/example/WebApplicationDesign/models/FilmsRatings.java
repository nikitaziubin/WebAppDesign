package com.example.WebApplicationDesign.models;

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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
}