package com.example.WebApplicationDesign.Emails;


import com.example.WebApplicationDesign.Films.Film;
import com.example.WebApplicationDesign.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.from:}")
    private String fromAddress;

    @Async
    public void sendPaymentConfirmation(User user, Film film, double amountEur) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            return;
        }

        String subject = "Payment confirmation for " + film.getName();
        String text = """
                Hello %s,

                Thank you for your purchase!

                Film: %s
                Price: %.2f €

                You can now access this film in your account.

                Best regards,
                Your Film Catalog Team
                """.formatted(user.getName(), film.getName(), amountEur);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(user.getEmail());
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}