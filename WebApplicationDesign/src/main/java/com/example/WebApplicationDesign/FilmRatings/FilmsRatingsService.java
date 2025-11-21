package com.example.WebApplicationDesign.FilmRatings;

import com.example.WebApplicationDesign.Films.FilmService;
import com.example.WebApplicationDesign.Series.SeriesService;
import com.example.WebApplicationDesign.ExceptionHandler.NoIdProvidedException;
import com.example.WebApplicationDesign.ExceptionHandler.NotFoundException;
import com.example.WebApplicationDesign.ExceptionHandler.OneRatingPerUserException;
import com.example.WebApplicationDesign.Films.Film;
import com.example.WebApplicationDesign.Users.User;
import com.example.WebApplicationDesign.Users.UsersService;
import com.example.WebApplicationDesign.shared.FilmUserAttachHelper;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class FilmsRatingsService {

    private final FilmService filmService;
    private final FilmUserAttachHelper filmUserAttachHelper;
    private final UsersService usersService;
    private FilmsRatingsRepository filmsRatingsRepository;
    private SeriesService seriesService;

    public List<FilmsRating> getFilmsRatings() {
        return filmsRatingsRepository.findAll();
    }
    public FilmsRating getFilmsRatingById(int id) {
        return filmsRatingsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("FilmsRating not found by id: " + id));
    }
    public FilmsRating createFilmsRating(@Valid FilmsRating filmsRating) {
        int userId = filmsRating.getUser().getId();
        User user = usersService.getUserById(userId);
        boolean createdUserFilmRatingOnThatFilm = user.getFilmsRatings().stream().anyMatch(fr ->
                fr.getFilm().getId().equals(filmsRating.getFilm().getId()));
        if(createdUserFilmRatingOnThatFilm) {
            throw new OneRatingPerUserException("User with id " + userId + " has already rated a film " + filmsRating.getFilm().getId());
        }
        filmUserAttachHelper.setFilmAndUser(filmsRating, filmsRating.getFilm(), user);
        return filmsRatingsRepository.save(filmsRating);
    }
    public FilmsRating updateFilmsRating(int id, FilmsRating newFilmsRating) {
        FilmsRating frToUpdate = getFilmsRatingById(id);
        frToUpdate.setRating(newFilmsRating.getRating());
        frToUpdate.setDateOfPublish(
                newFilmsRating.getDateOfPublish() == null ? new Date() : newFilmsRating.getDateOfPublish());
        filmUserAttachHelper.attachFilmAndUser(frToUpdate, newFilmsRating.getFilm(), newFilmsRating.getUser());

        return filmsRatingsRepository.save(frToUpdate);
    }
    public void deleteFilmsRating(int id) {
        if(!filmsRatingsRepository.existsById(id)) {
            throw new NotFoundException("FilmsRating not found by id: " + id);
        }
        filmsRatingsRepository.deleteById(id);
    }
    public List<FilmsRating> getFilmsRatingsByFilm(int filmId, int seriesId) {
        seriesService.getSeriesById(seriesId);
        filmService.getFilmById(filmId);
        return filmsRatingsRepository.findAllByFilmId(filmId);
    }
    public FilmsRating getFilmsRatingForFilm(int filmsRatingId, int filmId, int seriesId) {
        seriesService.getSeriesById(seriesId);
        filmService.getFilmById(filmId);
        return getFilmsRatingById(filmsRatingId);
    }
}
