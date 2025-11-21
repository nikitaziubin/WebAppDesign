package com.example.WebApplicationDesign.Trailers;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrailersRepository extends ListCrudRepository<Trailer, Integer> {
}
