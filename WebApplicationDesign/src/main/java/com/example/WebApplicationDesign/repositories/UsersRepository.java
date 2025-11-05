package com.example.WebApplicationDesign.repositories;

import com.example.WebApplicationDesign.models.Projections;
import com.example.WebApplicationDesign.models.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends ListCrudRepository<User, Integer> {
    boolean existsByEmailIgnoreCase(String email);
    User findByEmail(String email);
    List<Projections.UsersDTO> findAllByIdBetween(Integer fromId, Integer toId);
}
