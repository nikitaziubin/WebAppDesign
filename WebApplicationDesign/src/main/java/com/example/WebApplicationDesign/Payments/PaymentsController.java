package com.example.WebApplicationDesign.Payments;

import com.example.WebApplicationDesign.Emails.EmailService;
import com.example.WebApplicationDesign.Films.Film;
import com.example.WebApplicationDesign.Films.FilmService;
import com.example.WebApplicationDesign.Stripe.StripeService;
import com.example.WebApplicationDesign.Users.User;
import com.example.WebApplicationDesign.Users.UsersService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.ApiResource;
import com.stripe.net.Webhook;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {
    private final PaymentsService paymentsService;
    private final StripeService stripeService;
    private final FilmService filmService;
    private final UsersService usersService;
    private final EmailService emailService;
    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    PaymentsController(PaymentsService paymentsService, StripeService stripeService,
                       FilmService filmService, UsersService usersService,
                       EmailService emailService) {
        this.paymentsService = paymentsService;
        this.stripeService = stripeService;
        this.filmService = filmService;
        this.usersService = usersService;
        this.emailService = emailService;
    }

    public record CreatePaymentRequest(Integer filmId, Integer userId) {}
    public record CreatePaymentResponse(String clientSecret) {}

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
    @PostMapping("/create-payment-intent")
    public ResponseEntity<?> createPaymentIntent(@RequestBody CreatePaymentRequest req){
        if(req.filmId() == null || req.userId() == null){
            return ResponseEntity.badRequest().build();
        }
        Film film = filmService.getFilmById(req.filmId());
        long amountEuros = Math.round(film.getFilmPrice() * 100);
        try{
            PaymentIntent paymentIntent = stripeService.createPaymentIntent(req.filmId(), req.userId(),
                    amountEuros);
            return ResponseEntity.ok(new CreatePaymentResponse(paymentIntent.getClientSecret()));
        } catch (StripeException e){
            return ResponseEntity.internalServerError().body("STripe error" + e.getMessage());
        }
    }
    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestHeader("Stripe-Signature") String sigHeader,
                                                @RequestBody String payload) {
        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

        } catch (SignatureVerificationException e){
            return ResponseEntity.badRequest().body("Webhook error: " + e.getMessage());
        }
        if("payment_intent.succeeded".equals(event.getType())){
            String json = event.getData().getObject().toJson();
            PaymentIntent paymentIntent = ApiResource.GSON.fromJson(json, PaymentIntent.class);

            if(paymentIntent != null){
                int filmId = Integer.parseInt(paymentIntent.getMetadata().get("film_id"));
                int userId = Integer.parseInt(paymentIntent.getMetadata().get("user_id"));
                Film film = filmService.getFilmById(filmId);
                User user = usersService.getUserByIdInternal(userId);
                Payment payment = new Payment();
                payment.setFilm(film);
                payment.setUser(user);
                payment.setAmount((float) (paymentIntent.getAmount() / 100.0));
                payment.setPaymentMethod(paymentIntent.getPaymentMethod());
                payment.setTransactionStatus("succeeded");
                payment.setDateOfPublish(new Date());
                paymentsService.savePayment(payment);
                emailService.sendPaymentConfirmation(user, film, payment.getAmount());
            }
        }
        return ResponseEntity.ok("ok");
    }

}
