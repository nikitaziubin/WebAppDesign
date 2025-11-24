package com.example.WebApplicationDesign.Payments;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRepository extends ListCrudRepository<Payment, Integer> {
}
