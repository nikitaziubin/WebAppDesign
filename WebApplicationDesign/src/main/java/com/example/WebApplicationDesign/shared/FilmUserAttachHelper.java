package com.example.WebApplicationDesign.shared;

import com.example.WebApplicationDesign.ExceptionHandler.NoIdProvidedException;
import com.example.WebApplicationDesign.Films.Film;
import com.example.WebApplicationDesign.Films.FilmService;
import com.example.WebApplicationDesign.Users.User;
import com.example.WebApplicationDesign.Users.UsersService;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@AllArgsConstructor
public class FilmUserAttachHelper {
    private final EntityManager entityManager;
    private final FilmService filmsService;

    public void attachFilmAndUser(FilmUserAssignable target, Film newFilm, User newUser){
        if(newFilm == null || target.getFilm().getId() == null){
            throw new NoIdProvidedException("Film id not found");
        }
        if(newUser == null || target.getUser().getId() == null){
            throw new NoIdProvidedException("User id not found");
        }
        Film film = entityManager.getReference(Film.class, newFilm.getId());
        User user = entityManager.getReference(User.class, newUser.getId());

        target.setFilm(film);
        target.setUser(user);
    }

    public void setFilmAndUser(FilmUserAssignable target, Film film, User user){
        if (user == null || user.getId() == null) {
            throw new NoIdProvidedException("User id not found");
        }
        if (film == null || film.getId() == null) {
            throw new NoIdProvidedException("Film id not found");
        }
        target.setUser(user);
        target.setFilm(filmsService.getFilmById(film.getId()));
        if(target.getDateOfPublish() == null) {
            target.setDateOfPublish(new Date());
        }
    }
}
