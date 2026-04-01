package com.glowjar.backend.service;

import com.glowjar.backend.dto.CreateOrderItemRequest;
import com.glowjar.backend.dto.CreateOrderRequest;
import com.glowjar.backend.entities.Order;
import com.glowjar.backend.entities.OrderItem;
import com.glowjar.backend.interfaces.OrderRepo;
import org.springframework.stereotype.Service;
import com.glowjar.backend.service.InvoiceService;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepo orderRepo;
    private final EmailService emailService;
    private final InvoiceService invoiceService;

    public OrderService(OrderRepo orderRepo,
                        EmailService emailService,
                        InvoiceService invoiceService) {
        this.orderRepo = orderRepo;
        this.emailService = emailService;
        this.invoiceService = invoiceService;
    }

    public Order createOrder(CreateOrderRequest request) {
        Order order = new Order();

        order.setEmail(request.getEmail() != null ? request.getEmail().trim().toLowerCase() : null);
        order.setFullName(request.getFullName() != null ? request.getFullName().trim() : null);
        order.setPhone(request.getPhone() != null ? request.getPhone().trim() : null);
        order.setAddress(request.getAddress() != null ? request.getAddress().trim() : null);
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

        String firstName = savedOrder.getFullName();
        if (firstName != null && firstName.contains(" ")) {
            firstName = firstName.split(" ")[0];
        }

        try {
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

        try {
            ByteArrayInputStream pdfStream = invoiceService.generateInvoicePdf(savedOrder);

            emailService.sendInvoiceEmail(
                    savedOrder.getEmail(),
                    firstName,
                    String.valueOf(savedOrder.getId()),
                    pdfStream
            );
        } catch (Exception e) {
            System.out.println("INVOICE EMAIL FAILED: " + e.getMessage());
            e.printStackTrace();
        }

        return savedOrder;
    }
}