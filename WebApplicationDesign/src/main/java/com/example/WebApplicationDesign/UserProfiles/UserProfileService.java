package com.example.WebApplicationDesign.UserProfiles;

import com.example.WebApplicationDesign.ExceptionHandler.NotFoundException;
import com.example.WebApplicationDesign.Users.User;
import com.example.WebApplicationDesign.Users.UsersService;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Service
@AllArgsConstructor
public class UserProfileService {
    private final EntityManager entityManager;
    private final UserProfileRepository userProfileRepository;
    private final UsersService usersService;

    public List<UserProfile> getUserProfiles() {
        return userProfileRepository.findAll();
    }
    public UserProfile getUserProfileById(int id){
        return userProfileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("UserProfile not found by id: " + id));
    }
    public UserProfile createUserProfile(UserProfile userProfile){
        User user = usersService.getUserById(userProfile.getUser().getId());
        userProfile.setUser(null);
        UserProfile savedProfile = userProfileRepository.save(userProfile);
        user.setUserProfile(savedProfile);
        usersService.saveUser(user);

        return savedProfile;
    }

    public UserProfile getUserProfileForUser(int userId){
        return userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("UserProfile not found by user id: " + userId));
    }

    public UserProfile updateUserProfile(int id, UserProfile userProfile){
        UserProfile existingUserProfile = getUserProfileById(id);
        existingUserProfile.setBirthDate(userProfile.getBirthDate());
        existingUserProfile.setGender(userProfile.getGender());
        existingUserProfile.setCountry(userProfile.getCountry());
        existingUserProfile.setCity(userProfile.getCity());
        existingUserProfile.setAddress(userProfile.getAddress());
        existingUserProfile.setZipCode(userProfile.getZipCode());
        existingUserProfile.setRegion(userProfile.getRegion());
        if(userProfile.getUser() != null && userProfile.getUser().getId() != null) {
            usersService.getUserById(userProfile.getUser().getId());
            User user = entityManager.getReference(User.class, userProfile.getUser().getId());
            existingUserProfile.setUser(user);
        }
        return userProfileRepository.save(existingUserProfile);
    }
    public void deleteUserProfile(int id){
        UserProfile existingUserProfile = getUserProfileById(id);
        userProfileRepository.delete(existingUserProfile);
    }
}
