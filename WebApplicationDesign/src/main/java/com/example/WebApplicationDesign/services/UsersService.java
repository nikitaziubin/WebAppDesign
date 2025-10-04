package com.example.WebApplicationDesign.services;

import com.example.WebApplicationDesign.models.FilmsRating;
import com.example.WebApplicationDesign.models.Projections;
import com.example.WebApplicationDesign.models.User;
import com.example.WebApplicationDesign.repositories.FilmsRatingsRepository;
import com.example.WebApplicationDesign.repositories.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final FilmsRatingsRepository filmsRatingsRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getUsers() {
        return usersRepository.findAll();
    }

    public List<Projections.UsersDTO> getUsersNamesOnly() {
        return usersRepository.findAllByIdBetween(0, 10);
    }

    public User getUserById(int id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found by id: " + id));
    }

    public User createUser(User user) {
        String email = user.getEmail().trim().toLowerCase();
        if(usersRepository.existsByEmailIgnoreCase(email)) {
            throw new RuntimeException("Email already exists");
        }
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }
    public User updateUser(int id, User user) {
        User userToUpdate = usersRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found by id: " + id));
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setPhoneNumber(user.getPhoneNumber());
        return usersRepository.save(userToUpdate);
    }

    public void deleteUser(int id) {
        if(!usersRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found by id: " + id);
        }
        usersRepository.deleteById(id);
    }

    public List<FilmsRating> getFilmsRatingsByUser(int id) {
        return filmsRatingsRepository.findAllByUserId(id);
    }
}
