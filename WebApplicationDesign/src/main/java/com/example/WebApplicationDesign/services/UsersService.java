package com.example.WebApplicationDesign.services;

import com.example.WebApplicationDesign.models.Users;
import com.example.WebApplicationDesign.repositories.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Users> getUsers() {
        return usersRepository.findAll();
    }

    public Users getUserById(int id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found by id: " + id));
    }

    public Users createUser(Users user) {
        String email = user.getEmail().trim().toLowerCase();
        if(usersRepository.existsByEmailIgnoreCase(email)) {
            throw new RuntimeException("Email already exists");
        }
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }
    public Users updateUser(int id, Users user) {
        Users usersToUpdate = usersRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found by id: " + id));
        usersToUpdate.setName(user.getName());
        usersToUpdate.setEmail(user.getEmail());
        usersToUpdate.setPassword(user.getPassword());
        return usersRepository.save(usersToUpdate);
    }

    public void deleteUser(int id) {
        if(!usersRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found by id: " + id);
        }
        usersRepository.deleteById(id);
    }
}
