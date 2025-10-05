package com.example.WebApplicationDesign.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
    private int id;

    @Column(name = "date_of_publish", nullable = false)
    @CreationTimestamp
    private Date dateOfPublish = new Date();

    @Column(nullable = false)
    private int rating;

    @Column(name = "film_id", nullable = false)
    private int filmId;

    @Column(name = "user_id", insertable = false, updatable = false)
    private int userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;
}