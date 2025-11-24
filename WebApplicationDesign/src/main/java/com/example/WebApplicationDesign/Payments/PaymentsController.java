package com.example.WebApplicationDesign.Payments;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@AllArgsConstructor
public class PaymentsController {
    private PaymentsService paymentsService;

    @GetMapping
    public ResponseEntity<List<Payment>> getPayments() {
        return ResponseEntity.ok(paymentsService.getAllPayments());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable int id) {
        return ResponseEntity.ok(paymentsService.getPaymentById(id));
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Payment> createPayment(@RequestBody @Valid Payment payment) {
        Payment createdPayment = paymentsService.createPayment(payment);
        return ResponseEntity.created(URI.create("/api/payments/" + createdPayment.getId()))
                .body(createdPayment);
    }
}
