package com.glowjar.backend.controllers;

import com.glowjar.backend.dto.CreateOrderRequest;
import com.glowjar.backend.entities.Order;
import com.glowjar.backend.interfaces.OrderRepo;
import com.glowjar.backend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://glow-jars.vercel.app",
        "https://glow-jars-production.up.railway.app"
})
public class OrderController {

    private final OrderService orderService;
    private final OrderRepo orderRepo;

    public OrderController(OrderService orderService, OrderRepo orderRepo) {
        this.orderService = orderService;
        this.orderRepo = orderRepo;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) {
        Order savedOrder = orderService.createOrder(request);
        return ResponseEntity.ok(Map.of(
                "id", savedOrder.getId(),
                "status", savedOrder.getStatus(),
                "email", savedOrder.getEmail()
        ));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrdersByEmail(@RequestParam String email) {
        return ResponseEntity.ok(orderRepo.findByEmailOrderByCreatedAtDesc(email));
    }
}