package com.example.WebApplicationDesign.shared;

import com.example.WebApplicationDesign.Films.Film;
import com.example.WebApplicationDesign.Users.User;
import java.util.Date;

public interface FilmUserAssignable {
    void setFilm(Film film);
    void setUser(User user);
    void setDateOfPublish(Date date);

    Date getDateOfPublish();
    Film getFilm();
    User getUser();
}
