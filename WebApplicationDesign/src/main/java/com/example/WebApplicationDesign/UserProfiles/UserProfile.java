package com.example.WebApplicationDesign.UserProfiles;

import com.example.WebApplicationDesign.Users.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table
@Data
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Date birthDate;
    @Column(nullable = false)
    @NotBlank
    private String gender;
    @Column(nullable = false)
    @NotBlank
    private String country;
    @Column(nullable = false)
    @NotBlank
    private String city;
    @Column(nullable = false)
    @NotBlank
    private String address;
    @Column(nullable = false)
    @NotBlank
    private String zipCode;
    @Column(nullable = false)
    @NotBlank
    private String region;

    @OneToOne(mappedBy = "userProfile")
    private User user;

}
