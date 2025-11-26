package com.example.WebApplicationDesign.Descriptions;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WikiDescriptionRepository extends ListCrudRepository<WikiDescription, Integer> {
    Optional<WikiDescription> findByFilmId(int filmId);
}
