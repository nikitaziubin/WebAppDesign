package com.example.WebApplicationDesign.controllers;

import com.example.WebApplicationDesign.models.FilmsRatings;
import com.example.WebApplicationDesign.models.Users;
import com.example.WebApplicationDesign.services.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @GetMapping
    public ResponseEntity<List<Users>> getUsers(){
        return ResponseEntity.ok(usersService.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUser(@PathVariable int id){
        return ResponseEntity.ok(usersService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<Users> addUser(@RequestBody @Valid Users user){
        Users createdUser = usersService.createUser(user);
        return ResponseEntity
                .created(URI.create("/api/users/" + createdUser.getId()))
                .body(createdUser);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@RequestBody @Valid Users user, @PathVariable int id){
        Users updatedUser = usersService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Users> deleteUser(@PathVariable int id){
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/films-ratings")
    public ResponseEntity<List<FilmsRatings>> getFilmsRatingsByUser(@PathVariable int id){
        return ResponseEntity.ok(usersService.getFilmsRatingsByUser(id));
    }
}
