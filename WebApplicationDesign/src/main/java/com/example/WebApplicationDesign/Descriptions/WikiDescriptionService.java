package com.example.WebApplicationDesign.Descriptions;

import com.example.WebApplicationDesign.ExceptionHandler.NotFoundException;
import com.example.WebApplicationDesign.Films.Film;
import com.example.WebApplicationDesign.Films.FilmService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WikiDescriptionService {
    private final WikiDescriptionRepository descriptionRepository;
    private final FilmService filmService;

    public List<WikiDescription> getAllWikiDescription(){
        return descriptionRepository.findAll();
    }
    public WikiDescription getWikiDescriptionById(int id){
        return descriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Description not found by id: " + id));
    }
    public WikiDescription getWikiDescriptionByFilmId(int filmId){
        return descriptionRepository.findByFilmId(filmId)
                .orElseThrow(() -> new NotFoundException("Description not found by film id: " + filmId));
    }
    public WikiDescription createWikiDescriptionToFilmID(int filmId){
        Film film = filmService.getFilmById(filmId);
        if(film.getDescription() != null){
            throw new IllegalStateException("Film " + film.getName() + " already has a description.");
        }
        WikiDescription description = new WikiDescription();

        Jwiki jwiki = new Jwiki(film.getName());
        description.setDescriptionText(jwiki.getExtractText());
        description.setFilm(film);
        film.setDescription(description);
        return descriptionRepository.save(description);
    }
}
