package com.glowjar.backend.controllers;

import com.glowjar.backend.dto.CheckoutRequest;
import com.glowjar.backend.service.CheckoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody CheckoutRequest request) {
        checkoutService.placeOrder(request);

        return ResponseEntity.ok(Map.of(
                "message", "Comanda a fost procesata. Confirmarea si factura au fost trimise pe email."
        ));
    }
}