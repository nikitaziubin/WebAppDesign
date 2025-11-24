package com.example.WebApplicationDesign.Payments;

import com.example.WebApplicationDesign.Films.Film;
import com.example.WebApplicationDesign.Users.User;
import com.example.WebApplicationDesign.shared.FilmUserAssignable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table
public class Payment implements FilmUserAssignable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    @NotNull private float amount;
    @Column(nullable = false)
    @NotNull private String paymentMethod;
    @Column(nullable = false)
    @NotNull private String transactionStatus;
    @Column(nullable = false)
    @NotNull private Date dateOfPublish;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"filmsRatings", "filmsComments", "trailers", "password", "hibernateLazyInitializer", "handler"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "film_id", nullable = true)
    @JsonIgnoreProperties({"payments", "series", "productionCompany", "genres", "filmsComments", "filmsRatings", "trailers", "hibernateLazyInitializer", "handler"})
    private Film film;

}
