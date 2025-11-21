package com.example.WebApplicationDesign.FilmComments;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmsCommentsRepository extends ListCrudRepository<FilmsComment, Integer> {
}
