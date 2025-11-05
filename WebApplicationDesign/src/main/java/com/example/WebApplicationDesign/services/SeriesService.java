package com.example.WebApplicationDesign.services;

import com.example.WebApplicationDesign.exceptionHandler.NotFoundException;
import com.example.WebApplicationDesign.models.Series;
import com.example.WebApplicationDesign.repositories.SeriesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class SeriesService {
    SeriesRepository seriesRepository;

    public List<Series> getAllSeries() {
        return seriesRepository.findAll();
    }
    public Series getSeriesById(int id) {
        return seriesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Series not found by id: " + id));
    }
    public Series createSeries(Series series) {
        if(series.getDateOfPublish() == null) {
            series.setDateOfPublish(new Date());
        }
        return seriesRepository.save(series);
    }

    public Series updateSeries(Series series, int id) {
        Series seriesToUpdate = getSeriesById(id);
        seriesToUpdate.setName(series.getName());
        seriesToUpdate.setAgeLimit(series.getAgeLimit());
        seriesToUpdate.setDateOfPublish(
                series.getDateOfPublish() == null ? new Date() : series.getDateOfPublish());
        seriesToUpdate.setCountryOfProduction(series.getCountryOfProduction());
        seriesToUpdate.setProductionCompanyName(series.getProductionCompanyName());
        seriesToUpdate.setStatus(series.getStatus());
        seriesToUpdate.setNumberOfEpisodes(series.getNumberOfEpisodes());
        return seriesRepository.save(seriesToUpdate);
    }
    public void deleteSeries(int id) {
        if(!seriesRepository.existsById(id)) {
            throw new NotFoundException("Series not found by id: " + id);
        }
        seriesRepository.deleteById(id);
    }
}
