package com.example.WebApplicationDesign.FilmComments;

import com.example.WebApplicationDesign.Films.Film;
import com.example.WebApplicationDesign.Users.User;
import com.example.WebApplicationDesign.shared.FilmUserAssignable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "FilmsComments")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FilmsComment implements FilmUserAssignable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    @NotNull private String textOfComment;
    @Column(nullable = false)
    private Date dateOfPublish;
    @Column(nullable = false)
    @NotNull private boolean spoiler;
    @NotNull private String language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false)
    @JsonIgnoreProperties({"filmsComments", "hibernateLazyInitializer", "handler"})
    @NotNull private Film film;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"filmsComments", "hibernateLazyInitializer", "handler"})
    @NotNull private User user;

}
