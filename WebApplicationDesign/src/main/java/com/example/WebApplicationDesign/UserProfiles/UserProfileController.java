package com.example.WebApplicationDesign.UserProfiles;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/user-profiles")
@AllArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;

    @GetMapping
    public ResponseEntity<List<UserProfile>> getUserProfiles() {
        return ResponseEntity.ok(userProfileService.getUserProfiles());
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable int id) {
        return ResponseEntity.ok(userProfileService.getUserProfileById(id));
    }

    @GetMapping("/my-profile/{userId}")
    public ResponseEntity<UserProfile> getUserProfileByUserId(@PathVariable int userId) {
        return ResponseEntity.ok(userProfileService.getUserProfileForUser(userId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfile> createUserProfile(@RequestBody @Valid UserProfile userProfile) {
        UserProfile createdUserProfile = userProfileService.createUserProfile(userProfile);
        return ResponseEntity.created(URI.create("/api/user-profiles/" + createdUserProfile.getId()))
                .body(createdUserProfile);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserProfile> updateUserProfile(@RequestBody @Valid UserProfile userProfile, @PathVariable int id) {
        UserProfile updatedUserProfile = userProfileService.updateUserProfile(id, userProfile);
        return ResponseEntity.ok(updatedUserProfile);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<UserProfile> deleteUserProfile(@PathVariable int id) {
        userProfileService.deleteUserProfile(id);
        return ResponseEntity.noContent().build();
    }
}
