package com.example.WebApplicationDesign.services;

import com.example.WebApplicationDesign.exceptionHandler.NotFoundException;
import com.example.WebApplicationDesign.models.FilmsRating;
import com.example.WebApplicationDesign.models.User;
import com.example.WebApplicationDesign.repositories.FilmsRatingsRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class FilmsRatingsService {
    private final UsersService usersService;
    private FilmsRatingsRepository filmsRatingsRepository;

    public List<FilmsRating> getFilmsRatings() {
        return filmsRatingsRepository.findAll();
    }
    public FilmsRating getFilmsRatingById(int id) {
        return filmsRatingsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("FilmsRating not found"));
    }
    public FilmsRating createFilmsRating(@Valid FilmsRating filmsRating) {
        User user = usersService.getUserById(filmsRating.getUserId());
        filmsRating.setUser(user);
        if(filmsRating.getDateOfPublish() == null) {
            filmsRating.setDateOfPublish(new Date());
        }
        return filmsRatingsRepository.save(filmsRating);
    }
    public FilmsRating updateFilmsRating(int id, FilmsRating newFilmsRating) {
        FilmsRating frToUpdate = getFilmsRatingById(id);
        frToUpdate.setRating(newFilmsRating.getRating());
        frToUpdate.setFilmId(newFilmsRating.getFilmId());
        if(newFilmsRating.getUser() != null) {
            frToUpdate.setUser(newFilmsRating.getUser());
        }
        return filmsRatingsRepository.save(frToUpdate);
    }
    public void deleteFilmsRating(int id) {
        if(!filmsRatingsRepository.existsById(id)) {
            throw new NotFoundException("FilmsRating not found by id: " + id);
        }
        filmsRatingsRepository.deleteById(id);
    }
}
