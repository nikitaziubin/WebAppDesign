package com.example.WebApplicationDesign.Users;

import com.example.WebApplicationDesign.FilmComments.FilmsComment;
import com.example.WebApplicationDesign.FilmRatings.FilmsRating;
import com.example.WebApplicationDesign.Payments.Payment;
import com.example.WebApplicationDesign.RefreshTokens.RefreshToken;
import com.example.WebApplicationDesign.UserProfiles.UserProfile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;



@Entity
@Table(
        name = "Users",
        uniqueConstraints = @UniqueConstraint(name = "uk_users_email", columnNames = "email")
)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    public enum USER_ROLES{ LOGGED_IN, ADMIN }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotBlank private String name;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank private String password;

    @Column(nullable = false )
    @NotBlank private String phoneNumber;

    @Column(nullable = false, unique = true)
    @Email private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private USER_ROLES role;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user", "hibernateLazyInitializer", "handler"})
    private List<FilmsRating> filmsRatings = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user", "hibernateLazyInitializer", "handler"})
    private List<FilmsComment> filmsComments = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<RefreshToken> refreshTokens = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user", "hibernateLazyInitializer", "handler"})
    private List<Payment> payments = new ArrayList<>();

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_profile_id", referencedColumnName = "id", nullable = true)
    @JsonIgnoreProperties({"user"})
    private UserProfile userProfile;
}
