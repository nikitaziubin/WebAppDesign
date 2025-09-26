package com.example.WebApplicationDesign.repositories;

import com.example.WebApplicationDesign.models.Users;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends ListCrudRepository<Users, Integer> {
    boolean existsByEmailIgnoreCase(String email);
}
