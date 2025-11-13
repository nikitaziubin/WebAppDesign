package com.example.WebApplicationDesign.services;

import com.example.WebApplicationDesign.exceptionHandler.NotFoundException;
import com.example.WebApplicationDesign.models.Film;
import com.example.WebApplicationDesign.models.Series;
import com.example.WebApplicationDesign.repositories.FilmsRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class FilmService {
    EntityManager entityManager;
    private final FilmsRepository filmsRepository;
    private final SeriesService seriesService;

    public List<Film> getAllFilms() {
        //return filmsRepository.findAll();
        return filmsRepository.findFilmsBySeriesIsNull();
    }

    public Film getFilmById(int id) {
        return filmsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Film not found by id: " + id));
    }
    public Film createFilm(Film film) {
        if(film.getDateOfPublish() == null) {
            film.setDateOfPublish(new Date());
        }
        if (film.getSeries() != null && film.getSeries().getId() != null) {
            Series series = seriesService.getSeriesById(film.getSeries().getId());
            film.setSeries(series);
        }
        else {
            film.setSeries(null);
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

        if(film.getSeries() != null && film.getSeries().getId() != null){
            seriesService.getSeriesById(film.getSeries().getId());
            Series series = entityManager.getReference(Series.class, film.getSeries().getId());
            filmToUpdate.setSeries(series);
        }
        else{
            filmToUpdate.setSeries(null);
        }
        return filmsRepository.save(filmToUpdate);
    }
    public void deleteFilm(int id) {
        if(!filmsRepository.existsById(id)) {
            throw new NotFoundException("Film not found by id: " + id);
        }
        filmsRepository.deleteById(id);
    }
    public List<Film> getFilmsBySeries(int seriesId) {
        Series series = seriesService.getSeriesById(seriesId);
        return filmsRepository.findFilmsBySeriesId(series.getId());
    }
    public Film getFilmInSeries(int seriesId, int filmId) {
        Series series = seriesService.getSeriesById(seriesId);
        Film film = getFilmById(filmId);
        if(film.getSeries() == null || film.getSeries().getId() != series.getId()) {
            throw new NotFoundException("Film with id: " + filmId + " not found in series with id: " + seriesId);
        }
        return film;
    }
}


