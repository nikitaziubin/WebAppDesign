package com.example.WebApplicationDesign.services;

import com.example.WebApplicationDesign.exceptionHandler.NoIdProvidedException;
import com.example.WebApplicationDesign.exceptionHandler.NotFoundException;
import com.example.WebApplicationDesign.models.Film;
import com.example.WebApplicationDesign.models.FilmsRating;
import com.example.WebApplicationDesign.models.User;
import com.example.WebApplicationDesign.repositories.FilmsRatingsRepository;
import com.example.WebApplicationDesign.repositories.FilmsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class FilmsRatingsService {

    EntityManager entityManager;
    private final UsersService usersService;
    private FilmsRatingsRepository filmsRatingsRepository;
    private FilmService filmsService;

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
        filmsRating.setUser(user);
        int filmId = filmsRating.getFilm().getId();
        Film film = filmsService.getFilmById(filmId);
        filmsRating.setFilm(film);
        if(filmsRating.getDateOfPublish() == null) {
            filmsRating.setDateOfPublish(new Date());
        }
        return filmsRatingsRepository.save(filmsRating);
    }
    public FilmsRating updateFilmsRating(int id, FilmsRating newFilmsRating) {
        FilmsRating frToUpdate = getFilmsRatingById(id);
        frToUpdate.setRating(newFilmsRating.getRating());

        frToUpdate.setDateOfPublish(
                newFilmsRating.getDateOfPublish() == null ? new Date() : newFilmsRating.getDateOfPublish());

        if(newFilmsRating.getFilm().getId() == null){
            throw new NoIdProvidedException("Film id not found");
        }
        Film film = entityManager.getReference(Film.class, newFilmsRating.getFilm().getId());
        frToUpdate.setFilm(film);

        if(newFilmsRating.getUser().getId() == null){
            throw new NoIdProvidedException("User id not found");
        }
        User user = entityManager.getReference(User.class, newFilmsRating.getUser().getId());
        frToUpdate.setUser(user);

        return filmsRatingsRepository.save(frToUpdate);
    }
    public void deleteFilmsRating(int id) {
        if(!filmsRatingsRepository.existsById(id)) {
            throw new NotFoundException("FilmsRating not found by id: " + id);
        }
        filmsRatingsRepository.deleteById(id);
    }
    public List<FilmsRating> getFilmsRatingsByFilm(int filmId) {
        return filmsRatingsRepository.findAllByFilmId(filmId);
    }
    public FilmsRating getFilmsRatingForFilm(int filmsRatingId) {
        return getFilmsRatingById(filmsRatingId);
    }
}
