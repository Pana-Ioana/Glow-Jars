package com.glowjar.backend.service;

import com.glowjar.backend.dto.CreateOrderItemRequest;
import com.glowjar.backend.dto.CreateOrderRequest;
import com.glowjar.backend.entities.Order;
import com.glowjar.backend.entities.OrderItem;
import com.glowjar.backend.interfaces.OrderRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepo orderRepo;
    private final EmailService emailService;

    public OrderService(OrderRepo orderRepo, EmailService emailService) {
        this.orderRepo = orderRepo;
        this.emailService = emailService;
    }

    public Order createOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setEmail(request.getEmail());
        order.setFullName(request.getFullName());
        order.setPhone(request.getPhone());
        order.setAddress(request.getAddress());
        order.setTotal(request.getTotal());
        order.setStatus("PLASATA");
        order.setCreatedAt(LocalDateTime.now());

        if (request.getItems() != null) {
            for (CreateOrderItemRequest itemRequest : request.getItems()) {
                OrderItem item = new OrderItem();
                item.setOrder(order);
                item.setProductName(itemRequest.getProductName());
                item.setQuantity(itemRequest.getQuantity());
                item.setPrice(itemRequest.getPrice());
                item.setCustomization(itemRequest.getCustomization());
                order.getItems().add(item);
            }
        }

        Order savedOrder = orderRepo.save(order);

        try {
            String firstName = savedOrder.getFullName();
            if (firstName != null && firstName.contains(" ")) {
                firstName = firstName.split(" ")[0];
            }

            emailService.sendOrderConfirmation(
                    savedOrder.getEmail(),
                    firstName,
                    String.valueOf(savedOrder.getId()),
                    savedOrder.getTotal()
            );
        } catch (Exception e) {
            System.out.println("ORDER CONFIRMATION EMAIL FAILED: " + e.getMessage());
            e.printStackTrace();
        }

        return savedOrder;
    }
}