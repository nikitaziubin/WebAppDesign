package com.example.WebApplicationDesign.RefreshTocken;

import com.example.WebApplicationDesign.config.JwtUtil;
import com.example.WebApplicationDesign.exceptionHandler.InvalidUsernameOrPasswordException;
import com.example.WebApplicationDesign.exceptionHandler.NotFoundException;
import com.example.WebApplicationDesign.exceptionHandler.RefreshTokenDTO;
import com.example.WebApplicationDesign.models.User;
import com.example.WebApplicationDesign.services.UsersService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final TokenService tokenService;
    private final UsersService usersService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/refresh-tokens")
    public ResponseEntity<LoginResponseDTO> refreshToken(@RequestBody RefreshTokenDTO refreshToken) {
        return ResponseEntity.ok(tokenService.validateAndRefreshAccessToken(refreshToken.refreshToken()));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid User user){
        try{
            User userToLogin = usersService.getUserByEmail(user.getEmail());
            if (passwordEncoder.matches(user.getPassword(), userToLogin.getPassword())) {
                String role = userToLogin.getRole().toString();
                String accessToken = jwtUtil.generateAccessToken(user.getEmail(), role);
                String refreshToken = tokenService.createRefreshToken(userToLogin);
                return ResponseEntity.ok(new LoginResponseDTO(refreshToken, accessToken));
            }
            else {
                throw new InvalidUsernameOrPasswordException("Invalid email or password");
            }
        }catch (NotFoundException e){
            throw new InvalidUsernameOrPasswordException("Invalid email or password");
        }
    }
    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> register(@RequestBody @Valid User user){
        User createdUser = usersService.createUser(user);
        return ResponseEntity
                .created(URI.create("/api/users/" + createdUser.getId()))
                .body(createdUser);
    }
}
