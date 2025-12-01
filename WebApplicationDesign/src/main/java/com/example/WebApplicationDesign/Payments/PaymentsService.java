package com.example.WebApplicationDesign.Payments;

import com.example.WebApplicationDesign.ExceptionHandler.NotFoundException;
import com.example.WebApplicationDesign.Users.User;
import com.example.WebApplicationDesign.Users.UsersService;
import com.example.WebApplicationDesign.shared.FilmUserAttachHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentsService {
    private final PaymentsRepository paymentsRepository;
    private final UsersService usersService;
    private final FilmUserAttachHelper filmUserAttachHelper;

    public List<Payment> getAllPayments(){
        return paymentsRepository.findAll();
    }

    public Payment getPaymentById(int id){
        return paymentsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found by id: " + id));
    }

    public Payment createPayment(Payment payment) {
        int userId = payment.getUser().getId();
        User user = usersService.getUserById(userId);
        filmUserAttachHelper.setFilmAndUser(payment, payment.getFilm(), user);
        return paymentsRepository.save(payment);
    }
    public void savePayment(Payment payment) {
        paymentsRepository.save(payment);
    }
}
