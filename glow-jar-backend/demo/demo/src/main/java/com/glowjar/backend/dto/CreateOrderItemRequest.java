package com.glowjar.backend.dto;

public class CreateOrderItemRequest {

    private String productName;
    private Integer quantity;
    private Double price;
    private String customization;

    public CreateOrderItemRequest() {}

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public String getCustomization() { return customization; }
    public void setCustomization(String customization) { this.customization = customization; }
}