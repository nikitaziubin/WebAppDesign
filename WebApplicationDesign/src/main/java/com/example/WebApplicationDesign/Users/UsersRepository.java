package com.example.WebApplicationDesign.Users;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends ListCrudRepository<User, Integer> {
    boolean existsByEmailIgnoreCase(String email);
    User findByEmail(String email);
    List<Projections.UsersDTO> findAllByIdBetween(Integer fromId, Integer toId);
}
