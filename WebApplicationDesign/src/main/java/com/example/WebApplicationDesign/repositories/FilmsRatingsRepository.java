package com.example.WebApplicationDesign.repositories;

import com.example.WebApplicationDesign.models.FilmsRatings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmsRatingsRepository extends ListCrudRepository<FilmsRatings, Integer> {
    List<FilmsRatings> findAllByUserId(int userId);
}
