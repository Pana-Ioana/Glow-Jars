package com.glowjar.backend.dto;

import java.util.List;

public class CreateOrderRequest {

    private String email;
    private String fullName;
    private String phone;
    private String address;
    private Double total;
    private List<CreateOrderItemRequest> items;

    public CreateOrderRequest() {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    public List<CreateOrderItemRequest> getItems() { return items; }
    public void setItems(List<CreateOrderItemRequest> items) { this.items = items; }
}