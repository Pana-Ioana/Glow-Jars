package com.glowjar.backend.dto;

public class ProductOptionDto {
    private Long id;
    private String size;
    private Integer piecesCount;
    private Double price;
    private Boolean available;

    public ProductOptionDto(Long id, String size, Integer piecesCount, Double price, Boolean available) {
        this.id = id;
        this.size = size;
        this.piecesCount = piecesCount;
        this.price = price;
        this.available = available;
    }

    public Long getId() { return id; }
    public String getSize() { return size; }
    public Integer getPiecesCount() { return piecesCount; }
    public Double getPrice() { return price; }
    public Boolean getAvailable() { return available; }
}