package com.example.WebApplicationDesign.services;

import com.example.WebApplicationDesign.models.FilmsRatings;
import com.example.WebApplicationDesign.models.Users;
import com.example.WebApplicationDesign.repositories.FilmsRatingsRepository;
import com.example.WebApplicationDesign.repositories.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class FilmsRatingsService {
    private final UsersRepository usersRepository;
    private final UsersService usersService;
    private FilmsRatingsRepository filmsRatingsRepository;

    public List<FilmsRatings> getFilmsRatings() {
        return filmsRatingsRepository.findAll();
    }
    public FilmsRatings getFilmsRatingById(int id) {
        return filmsRatingsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("FilmsRating not found"));
    }
    public FilmsRatings createFilmsRating(FilmsRatings filmsRating) {
        Users user = usersService.getUserById(filmsRating.getUserId());
        filmsRating.setUser(user);
        if(filmsRating.getDateOfPublish() == null) {
            filmsRating.setDateOfPublish(new Date());
        }
        return filmsRatingsRepository.save(filmsRating);
    }
    public FilmsRatings updateFilmsRating(int id, FilmsRatings newFilmsRating) {
        FilmsRatings frToUpdate = getFilmsRatingById(id);
        frToUpdate.setRating(newFilmsRating.getRating());
        frToUpdate.setFilmId(newFilmsRating.getFilmId());
        if(newFilmsRating.getUser() != null) {
            frToUpdate.setUser(newFilmsRating.getUser());
        }
        return filmsRatingsRepository.save(frToUpdate);
    }
    public void deleteFilmsRating(int id) {
        if(!filmsRatingsRepository.existsById(id)) {
            throw new EntityNotFoundException("FilmsRating not found by id: " + id);
        }
        filmsRatingsRepository.deleteById(id);
    }
}
