package com.example.WebApplicationDesign.Films;

import com.example.WebApplicationDesign.ExceptionHandler.NotFoundException;
import com.example.WebApplicationDesign.Genres.Genre;
import com.example.WebApplicationDesign.ProductionCompanies.ProductionCompanies;
import com.example.WebApplicationDesign.ProductionCompanies.ProductionCompaniesService;
import com.example.WebApplicationDesign.Series.Series;
import com.example.WebApplicationDesign.Series.SeriesService;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FilmService {
    EntityManager entityManager;
    private final FilmsRepository filmsRepository;
    private final SeriesService seriesService;
    private final ProductionCompaniesService productionCompaniesService;

    public List<Film> getAllFilms() {
        return filmsRepository.findAll();
        //return filmsRepository.findFilmsBySeriesIsNull();
    }

    public Film getFilmById(int id) {
        return filmsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Film not found by id: " + id));
    }
    public Film createFilm(Film film) {
        if(film.getDateOfPublish() == null) {
            film.setDateOfPublish(new Date());
        }
        if (film.getSeries() != null && film.getSeries().getId() != null) {
            Series series = seriesService.getSeriesById(film.getSeries().getId());
            film.setSeries(series);
        }
        else {
            film.setSeries(null);
        }
        if(film.getProductionCompany() != null && film.getProductionCompany().getId() != null){
            int id = film.getProductionCompany().getId();
            ProductionCompanies productionCompany = productionCompaniesService.getProductionCompaniesById(id);
            film.setProductionCompany(productionCompany);
        }
        else {
            film.setProductionCompany(null);
        }
        handleGenres(film, film);
        return filmsRepository.save(film);
    }
    public Film updateFilm(int id, Film film) {
        Film filmToUpdate = getFilmById(id);
        filmToUpdate.setName(film.getName());
        filmToUpdate.setQuality(film.getQuality());
        filmToUpdate.setDuration(film.getDuration());
        filmToUpdate.setPreviewPhoto(film.getPreviewPhoto());
        filmToUpdate.setAgeLimit(film.getAgeLimit());
        filmToUpdate.setDateOfPublish(
                film.getDateOfPublish() == null ? new Date() : film.getDateOfPublish());
        filmToUpdate.setBudget(film.getBudget());
        filmToUpdate.setLanguage(film.getLanguage());
        filmToUpdate.setFilmPrice(film.getFilmPrice());

        if(film.getSeries() != null && film.getSeries().getId() != null){
            seriesService.getSeriesById(film.getSeries().getId());
            Series series = entityManager.getReference(Series.class, film.getSeries().getId());
            filmToUpdate.setSeries(series);
        }
        else{
            filmToUpdate.setSeries(null);
        }
        if(film.getProductionCompany() != null && film.getProductionCompany().getId() != null){
            ProductionCompanies productionCompany = entityManager.getReference(ProductionCompanies.class, film.getProductionCompany().getId());
            filmToUpdate.setProductionCompany(productionCompany);
        }
        else {
            filmToUpdate.setProductionCompany(null);
        }
        handleGenres(film, filmToUpdate);

        return filmsRepository.save(filmToUpdate);
    }
    public void deleteFilm(int id) {
        if(!filmsRepository.existsById(id)) {
            throw new NotFoundException("Film not found by id: " + id);
        }
        filmsRepository.deleteById(id);
    }
    public List<Film> getFilmsBySeries(int seriesId) {
        Series series = seriesService.getSeriesById(seriesId);
        return filmsRepository.findFilmsBySeriesId(series.getId());
    }
    public Film getFilmInSeries(int seriesId, int filmId) {
        seriesService.getSeriesById(seriesId);
        return getFilmById(filmId);
    }

    private void handleGenres(Film film, Film sourceFilm) {
        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            var distinctGenres  = film.getGenres().stream()
                            .filter(g -> g.getId() != null)
                                    .collect(Collectors.toMap(
                                            Genre::getId, Function.identity(), (g1, g2) -> g1))
                            .values().stream()
                    .map(genre -> entityManager.getReference(Genre.class, genre.getId()))
                    .toList();
            sourceFilm.setGenres(new ArrayList<>(distinctGenres));
        } else {
            sourceFilm.setGenres(null);
        }
    }
}


