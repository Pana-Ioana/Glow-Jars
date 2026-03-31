package com.glowjar.backend.service;

import com.glowjar.backend.dto.CheckoutItemRequest;
import com.glowjar.backend.dto.CheckoutRequest;
import com.glowjar.backend.dto.InvoiceItemDto;
import com.glowjar.backend.dto.PreparedOrderDto;
import com.glowjar.backend.entities.Product;
import com.glowjar.backend.entities.ProductOption;
import com.glowjar.backend.interfaces.ProductOptionRepo;
import com.glowjar.backend.interfaces.ProductRepo;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CheckoutService {

    private final ProductRepo productRepo;
    private final ProductOptionRepo productOptionRepo;
    private final EmailService emailService;
    private final InvoiceService invoiceService;

    public CheckoutService(
            ProductRepo productRepo,
            ProductOptionRepo productOptionRepo,
            EmailService emailService,
            InvoiceService invoiceService
    ) {
        this.productRepo = productRepo;
        this.productOptionRepo = productOptionRepo;
        this.emailService = emailService;
        this.invoiceService = invoiceService;
    }

    public void placeOrder(CheckoutRequest request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new RuntimeException("Cosul este gol.");
        }

        List<InvoiceItemDto> invoiceItems = new ArrayList<>();
        double total = 0.0;

        for (CheckoutItemRequest itemRequest : request.getItems()) {
            Product product = productRepo.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Produsul nu exista: " + itemRequest.getProductId()));

            ProductOption option = resolveOption(product, itemRequest.getOptionId());

            int quantity = itemRequest.getQuantity() == null || itemRequest.getQuantity() < 1
                    ? 1
                    : itemRequest.getQuantity();

            double unitPrice = option.getPrice();
            double lineTotal = unitPrice * quantity;
            total += lineTotal;

            invoiceItems.add(new InvoiceItemDto(
                    product.getName(),
                    "Optiune selectata",
                    quantity,
                    unitPrice,
                    lineTotal
            ));
        }

        String orderNumber = "GJ-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        PreparedOrderDto preparedOrder = new PreparedOrderDto();
        preparedOrder.setOrderNumber(orderNumber);
        preparedOrder.setFirstName(request.getFirstName());
        preparedOrder.setLastName(request.getLastName());
        preparedOrder.setEmail(request.getEmail());
        preparedOrder.setPhone(request.getPhone());
        preparedOrder.setAddress(request.getAddress());
        preparedOrder.setCity(request.getCity());
        preparedOrder.setCounty(request.getCounty());
        preparedOrder.setTotal(total);
        preparedOrder.setItems(invoiceItems);

        emailService.sendOrderConfirmation(
                preparedOrder.getEmail(),
                preparedOrder.getFirstName(),
                preparedOrder.getOrderNumber(),
                preparedOrder.getTotal()
        );

        ByteArrayInputStream pdf = invoiceService.generateInvoice(preparedOrder);

        emailService.sendInvoiceEmail(
                preparedOrder.getEmail(),
                preparedOrder.getFirstName(),
                "factura-" + preparedOrder.getOrderNumber() + ".pdf",
                pdf
        );
    }

    private ProductOption resolveOption(Product product, Long optionId) {
        if (optionId != null) {
            ProductOption option = productOptionRepo.findById(optionId)
                    .orElseThrow(() -> new RuntimeException("Optiunea nu exista: " + optionId));

            if (!option.getProduct().getId().equals(product.getId())) {
                throw new RuntimeException("Optiunea nu apartine produsului selectat.");
            }

            return option;
        }

        if (product.getOptions() == null || product.getOptions().isEmpty()) {
            throw new RuntimeException("Produsul nu are optiuni disponibile.");
        }

        return product.getOptions().get(0);
    }
}