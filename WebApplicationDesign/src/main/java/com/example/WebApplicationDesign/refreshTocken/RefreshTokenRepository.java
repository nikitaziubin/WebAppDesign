package com.example.WebApplicationDesign.refreshTocken;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends ListCrudRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
}
