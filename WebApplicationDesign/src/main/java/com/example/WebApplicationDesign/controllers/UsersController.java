package com.example.WebApplicationDesign.controllers;

import com.example.WebApplicationDesign.config.JwtUtil;
import com.example.WebApplicationDesign.exceptionHandler.InvalidUsernameOrPasswordException;
import com.example.WebApplicationDesign.exceptionHandler.NotFoundException;
import com.example.WebApplicationDesign.models.Projections;
import com.example.WebApplicationDesign.models.User;
import com.example.WebApplicationDesign.services.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UsersController {

    private final UsersService usersService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(usersService.getUsers());
    }

    @GetMapping("/names-only")
    public ResponseEntity<List<Projections.UsersDTO>> getUsersNamesOnly(){
        return ResponseEntity.ok(usersService.getUsersNamesOnly());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id){
        return ResponseEntity.ok(usersService.getUserById(id));
    }

    @GetMapping("/list/{ids}")
    public ResponseEntity< List<Object>> getUser(@PathVariable List<Integer> ids){
        return ResponseEntity.ok(usersService.getUserList(ids));
    }

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> register(@RequestBody @Valid User user){
        User createdUser = usersService.createUser(user);
        return ResponseEntity
                .created(URI.create("/api/users/" + createdUser.getId()))
                .body(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid User user){
        try{
            User userToLogin = usersService.getUserByEmail(user.getEmail());
            if (passwordEncoder.matches(user.getPassword(), userToLogin.getPassword())) {
                String role = userToLogin.getRole().toString();
                String token = jwtUtil.generateToken(user.getEmail(), role);
                return ResponseEntity.ok(token);
            }
            else {
                throw new InvalidUsernameOrPasswordException("Invalid email or password");
            }
        }catch (NotFoundException e){
            throw new InvalidUsernameOrPasswordException("Invalid email or password");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody @Valid User user, @PathVariable int id){
        User updatedUser = usersService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id){
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
