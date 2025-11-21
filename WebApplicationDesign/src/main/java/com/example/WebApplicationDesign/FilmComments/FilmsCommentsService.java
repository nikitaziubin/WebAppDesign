package com.example.WebApplicationDesign.FilmComments;

import com.example.WebApplicationDesign.ExceptionHandler.NoIdProvidedException;
import com.example.WebApplicationDesign.ExceptionHandler.NotFoundException;
import com.example.WebApplicationDesign.Films.Film;
import com.example.WebApplicationDesign.Films.FilmService;
import com.example.WebApplicationDesign.Users.User;
import com.example.WebApplicationDesign.Users.UsersService;
import com.example.WebApplicationDesign.shared.FilmUserAttachHelper;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class FilmsCommentsService {
    private final FilmsCommentsRepository filmsCommentsRepository;
    private final UsersService usersService;
    private final FilmUserAttachHelper filmUserAttachHelper;

    public List<FilmsComment> getFilmsComments(){
        return filmsCommentsRepository.findAll();
    }
    public FilmsComment getFilmsComment(int id){
        return filmsCommentsRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("FilmsComment not found by id: " + id));
    }
    public FilmsComment createFilmsComment(@Valid FilmsComment filmsComment){
        int userId = filmsComment.getUser().getId();
        User user = usersService.getUserById(userId);
        filmUserAttachHelper.setFilmAndUser(filmsComment, filmsComment.getFilm(), user);
        return filmsCommentsRepository.save(filmsComment);
    }
    public FilmsComment updateFilmsComment(int id, @Valid FilmsComment filmsComment){
        FilmsComment fcToUpdate = getFilmsComment(id);
        fcToUpdate.setTextOfComment(filmsComment.getTextOfComment());
        fcToUpdate.setDateOfPublish(
                filmsComment.getDateOfPublish() == null ? new Date() : filmsComment.getDateOfPublish());
        fcToUpdate.setSpoiler(filmsComment.isSpoiler());
        fcToUpdate.setLanguage(filmsComment.getLanguage());

        filmUserAttachHelper.attachFilmAndUser(fcToUpdate, filmsComment.getFilm(), filmsComment.getUser());

        return filmsCommentsRepository.save(fcToUpdate);
    }
    public void deleteFilmsComment(int id){
        if(!filmsCommentsRepository.existsById(id)){
            throw new NotFoundException("FilmsComment not found by id: " + id);
        }
        filmsCommentsRepository.deleteById(id);
    }
}
