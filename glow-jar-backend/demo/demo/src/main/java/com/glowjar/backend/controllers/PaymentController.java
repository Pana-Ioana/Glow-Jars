package com.glowjar.backend.controllers;
import com.glowjar.backend.dto.PaymentIntentRequestDto;
import com.glowjar.backend.dto.PaymentIntentResponseDto;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://glow-jar-frontend.vercel.app"
})
public class PaymentController {

    @PostMapping("/create-payment-intent")
    public ResponseEntity<?> createPaymentIntent(@RequestBody PaymentIntentRequestDto request) {
        try {
            System.out.println("=== CREATE PAYMENT INTENT ===");
            System.out.println("Amount: " + request.getAmount());
            System.out.println("Currency: " + request.getCurrency());

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(request.getAmount())
                    .setCurrency(request.getCurrency())
                    .setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                    .setEnabled(true)
                                    .build()
                    )
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            System.out.println("Created PaymentIntent: " + paymentIntent.getId());

            return ResponseEntity.ok(new PaymentIntentResponseDto(paymentIntent.getClientSecret()));
        } catch (StripeException e) {
            e.printStackTrace();

            String stripeMessage = e.getMessage();
            String stripeCode = e.getCode();

            return ResponseEntity.badRequest().body(
                    "Stripe error: " + stripeMessage + " | code: " + stripeCode
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Server error: " + e.getMessage());
        }
    }
}