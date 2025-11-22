package com.example.WebApplicationDesign.RefreshTokens;

import com.example.WebApplicationDesign.config.JwtUtil;
import com.example.WebApplicationDesign.ExceptionHandler.RefreshTokenException;
import com.example.WebApplicationDesign.Users.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
public class TokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    @Value("${com.example.design.config.jwt.refresh-expiration}")
    private long expirationRefreshToken;

    public TokenService(RefreshTokenRepository refreshTokenRepository, JwtUtil jwtUtil) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtUtil = jwtUtil;
    }

    public String createRefreshToken(User user) {
        String NewToken = jwtUtil.generateRefreshToken(user.getId());
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(NewToken);
        refreshToken.setUser(user);
        refreshToken.setExpires(Instant.now().plusMillis(expirationRefreshToken));
        refreshTokenRepository.save(refreshToken);
        return NewToken;
    }
    public LoginResponseDTO validateAndRefreshAccessToken(String refreshToken){
        RefreshToken refreshTokenObj = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RefreshTokenException("Refresh token is invalid or expired"));
        if (Instant.now().isAfter(refreshTokenObj.getExpires())) {
            throw new RefreshTokenException("Refresh token is expired");
        }
        User user = refreshTokenObj.getUser();
        refreshTokenRepository.deleteById(refreshTokenObj.getId());
        String newAccessToken = jwtUtil.generateAccessToken(user.getId(), user.getRole().toString());
        String newRefreshToken = createRefreshToken(user);
        log.info("Refresh token validated and new tokens generated for user ID: {}", user.getId());
        return new LoginResponseDTO(newRefreshToken, newAccessToken);
    }
}