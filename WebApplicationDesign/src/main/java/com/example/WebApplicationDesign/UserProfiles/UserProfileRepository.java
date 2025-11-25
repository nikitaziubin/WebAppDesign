package com.example.WebApplicationDesign.UserProfiles;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends ListCrudRepository<UserProfile, Integer> {
    Optional<UserProfile> findByUserId(Integer userId);
}
