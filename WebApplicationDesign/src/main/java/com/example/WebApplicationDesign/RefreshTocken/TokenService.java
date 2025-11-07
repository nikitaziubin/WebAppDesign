package com.example.WebApplicationDesign.RefreshTocken;

import com.example.WebApplicationDesign.config.JwtUtil;
import com.example.WebApplicationDesign.exceptionHandler.RefreshTokenException;
import com.example.WebApplicationDesign.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

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
        String NewToken = jwtUtil.generateRefreshToken(user.getEmail());
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(NewToken);
        refreshToken.setUser(user);
        refreshToken.setExpires(Instant.now().plusMillis(expirationRefreshToken));
        refreshTokenRepository.save(refreshToken);
        return NewToken;
    }
    public LoginResponseDTO validateAndRefreshAccessToken(String refreshToken){
        Optional<RefreshToken> refreshTokenObj = refreshTokenRepository.findByToken(refreshToken);
        if(refreshTokenObj.isPresent()){
            Instant expires = refreshTokenObj.get().getExpires();
            if(Instant.now().isBefore(expires)){
                User user = refreshTokenObj.get().getUser();
                String newAccessToken = jwtUtil.generateAccessToken(user.getEmail(), user.getRole().toString());
                String newRefreshToken = createRefreshToken(user);
                refreshTokenRepository.deleteById(refreshTokenObj.get().getId());
                return new LoginResponseDTO(newRefreshToken, newAccessToken);
            }
        }
        throw new RefreshTokenException("Refresh token is invalid or expired");
    }
}