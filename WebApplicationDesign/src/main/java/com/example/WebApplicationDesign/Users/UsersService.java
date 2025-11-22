package com.example.WebApplicationDesign.Users;

import com.example.WebApplicationDesign.ExceptionHandler.EmailAlreadyExistException;
import com.example.WebApplicationDesign.ExceptionHandler.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getUsers() {
        return usersRepository.findAll();
    }

    public List<Projections.UsersDTO> getUsersNamesOnly() {
        return usersRepository.findAllByIdBetween(0, 10);
    }



    public List<Object> getUserList(List<Integer> ids){
        List <Object> results = new ArrayList<>();
        for(Integer id : ids){
            try {
                results.add(getUserById(id));
            } catch (NotFoundException e) {
                results.add(Map.of(
                        "status", "NOT_FOUND",
                        "requestedId", id,
                        "message", e.getMessage()
                ));
            }
        }
        return results;
    }

    private User findById(int id){
        return usersRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found by id: " + id));
    }
    public User getUserById(int id) {
        User userToView = findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        String loggedInUserId = authentication.getName();
        if(roles.contains("ADMIN") || loggedInUserId.equals(userToView.getId().toString())) {
            return userToView;
        }
        else{
            throw new AccessDeniedException("You do not have permission to access this resource.");
        }
    }
    public User getUserByEmail(String email) {
        User user = usersRepository.findByEmail(email);
        if (user == null) {
            throw new NotFoundException("User not found by email: " + email);
        }
        return user;
    }

    public User createUser(User user) {
        String email = user.getEmail().trim().toLowerCase();
        if(usersRepository.existsByEmailIgnoreCase(email)) {
            throw new EmailAlreadyExistException("Email already exists: " + email);
        }
        user.setRole(User.USER_ROLES.LOGGED_IN);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }
    public User updateUser(int id, User user) {
        User userToUpdate = getUserById(id);
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        userToUpdate.setPhoneNumber(user.getPhoneNumber());
        userToUpdate.setRole(user.getRole());
        return usersRepository.save(userToUpdate);
    }

    public void deleteUser(int id) {
        if(!usersRepository.existsById(id)) {
            throw new NotFoundException("User not found by id: " + id);
        }
        usersRepository.deleteById(id);
    }
}
