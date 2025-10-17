package com.example.WebApplicationDesign.services;

import com.example.WebApplicationDesign.exceptionHandler.NotFoundException;
import com.example.WebApplicationDesign.models.Film;
import com.example.WebApplicationDesign.repositories.FilmsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class FilmService {
    private final FilmsRepository filmsRepository;

    public List<Film> getAllFilms() {
        return filmsRepository.findAll();
    }

    public Film getFilmById(int id) {
        return filmsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found by id: " + id));
    }
    public Film createFilm(Film film) {
        if(film.getDateOfPublish() == null) {
            film.setDateOfPublish(new Date());
        }
        return filmsRepository.save(film);
    }
    public Film updateFilm(int id, Film film) {
        Film filmToUpdate = getFilmById(id);
        filmToUpdate.setName(film.getName());
        filmToUpdate.setQuality(film.getQuality());
        filmToUpdate.setDuration(film.getDuration());
        filmToUpdate.setPreviewPhoto(film.getPreviewPhoto());
        filmToUpdate.setAgeLimit(film.getAgeLimit());
        filmToUpdate.setDateOfPublish(
                film.getDateOfPublish() == null ? new Date() : film.getDateOfPublish());
        filmToUpdate.setBudget(film.getBudget());
        filmToUpdate.setLanguage(film.getLanguage());
        return filmsRepository.save(filmToUpdate);
    }
    public void deleteFilm(int id) {
        if(!filmsRepository.existsById(id)) {
            throw new NotFoundException("Film not found by id: " + id);
        }
        filmsRepository.deleteById(id);
    }
}
