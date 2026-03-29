package com.glowjar.backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "product_options")
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String size;
    private Integer piecesCount;
    private Double price;
    private Boolean available = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public ProductOption() {
    }

    public ProductOption(String size, Integer piecesCount, Double price, Boolean available) {
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
    public Product getProduct() { return product; }

    public void setId(Long id) { this.id = id; }
    public void setSize(String size) { this.size = size; }
    public void setPiecesCount(Integer piecesCount) { this.piecesCount = piecesCount; }
    public void setPrice(Double price) { this.price = price; }
    public void setAvailable(Boolean available) { this.available = available; }
    public void setProduct(Product product) { this.product = product; }
}