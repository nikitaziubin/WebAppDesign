package com.example.WebApplicationDesign.Trailers;

import com.example.WebApplicationDesign.ExceptionHandler.NoIdProvidedException;
import com.example.WebApplicationDesign.ExceptionHandler.NotFoundException;
import com.example.WebApplicationDesign.Films.Film;
import com.example.WebApplicationDesign.Films.FilmService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TrailersService {

    private TrailersRepository trailersRepository;
    private final FilmService filmService;

    public List<Trailer> getTrailers(){
        return trailersRepository.findAll();
    }
    public Trailer getTrailer(int id){
        return trailersRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Trailer not found by id: " + id));
    }
    public Trailer createTrailer(@Valid Trailer trailer){
        if (trailer.getFilm() == null || trailer.getFilm().getId() == null) {
            throw new NoIdProvidedException("Provide film id for the trailer");
        }
        Film film = filmService.getFilmById(trailer.getFilm().getId());
        trailer.setFilm(film);
        return trailersRepository.save(trailer);
    }
    public Trailer updateTrailer(int id, @Valid Trailer trailer){
        Trailer trailerToUpdate = getTrailer(id);
        trailerToUpdate.setTrailerUrl(trailer.getTrailerUrl());
        trailerToUpdate.setAgeLimit(trailer.getAgeLimit());
        trailerToUpdate.setDuration(trailer.getDuration());
        trailerToUpdate.setTitle(trailer.getTitle());
        if (trailer.getFilm() == null || trailer.getFilm().getId() == null) {
            throw new NoIdProvidedException("Provide film id for the trailer");
        }
        Film film = filmService.getFilmById(trailer.getFilm().getId());
        trailerToUpdate.setFilm(film);
        return trailersRepository.save(trailerToUpdate);
    }

    public void deleteTrailer(int id){
        if(!trailersRepository.existsById(id)){
            throw new NotFoundException("Trailer not found by id: " + id);
        }
        trailersRepository.deleteById(id);
    }
}
