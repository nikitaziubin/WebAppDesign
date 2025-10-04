package com.example.WebApplicationDesign.repositories;

import com.example.WebApplicationDesign.models.FilmsRating;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmsRatingsRepository extends ListCrudRepository<FilmsRating, Integer> {
    List<FilmsRating> findAllByUserId(int userId);
}
