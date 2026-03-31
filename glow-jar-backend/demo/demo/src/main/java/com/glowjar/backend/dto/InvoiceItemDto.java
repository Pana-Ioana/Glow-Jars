package com.glowjar.backend.dto;

public class InvoiceItemDto {
    private String productName;
    private String optionName;
    private Integer quantity;
    private Double unitPrice;
    private Double lineTotal;

    public InvoiceItemDto() {
    }

    public InvoiceItemDto(String productName, String optionName, Integer quantity, Double unitPrice, Double lineTotal) {
        this.productName = productName;
        this.optionName = optionName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.lineTotal = lineTotal;
    }

    public String getProductName() {
        return productName;
    }

    public String getOptionName() {
        return optionName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public Double getLineTotal() {
        return lineTotal;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setLineTotal(Double lineTotal) {
        this.lineTotal = lineTotal;
    }
}