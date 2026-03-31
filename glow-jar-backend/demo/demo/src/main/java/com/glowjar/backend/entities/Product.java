package com.glowjar.backend.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false)
    private String categorySlug;

    @Column(nullable = false)
    private String level;

    @Column(length = 300)
    private String shortDescription;

    @Column(length = 2000)
    private String description;

    @Column(length = 300)
    private String detailOne;

    @Column(length = 300)
    private String detailTwo;

    @Column(length = 300)
    private String detailThree;

    private String imageUrl;

    private Boolean bestseller = false;
    private Boolean newIn = false;
    private Boolean featured = false;

    private Double rating = 0.0;
    private Integer reviewsCount = 0;

    private String materialTone;
    private String vibe;

    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ProductOption> options = new ArrayList<>();

    public Product() {
    }

    public Product(String name, String slug, String categorySlug, String level,
                   String shortDescription, String description,
                   String detailOne, String detailTwo, String detailThree,
                   String imageUrl, Boolean bestseller, Boolean newIn, Boolean featured,
                   Double rating, Integer reviewsCount, String materialTone, String vibe) {
        this.name = name;
        this.slug = slug;
        this.categorySlug = categorySlug;
        this.level = level;
        this.shortDescription = shortDescription;
        this.description = description;
        this.detailOne = detailOne;
        this.detailTwo = detailTwo;
        this.detailThree = detailThree;
        this.imageUrl = imageUrl;
        this.bestseller = bestseller;
        this.newIn = newIn;
        this.featured = featured;
        this.rating = rating;
        this.reviewsCount = reviewsCount;
        this.materialTone = materialTone;
        this.vibe = vibe;
        this.createdAt = LocalDateTime.now();
    }

    public void addOption(ProductOption option) {
        option.setProduct(this);
        this.options.add(option);
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSlug() { return slug; }
    public String getCategorySlug() { return categorySlug; }
    public String getLevel() { return level; }
    public String getShortDescription() { return shortDescription; }
    public String getDescription() { return description; }
    public String getDetailOne() { return detailOne; }
    public String getDetailTwo() { return detailTwo; }
    public String getDetailThree() { return detailThree; }
    public String getImageUrl() { return imageUrl; }
    public Boolean getBestseller() { return bestseller; }
    public Boolean getNewIn() { return newIn; }
    public Boolean getFeatured() { return featured; }
    public Double getRating() { return rating; }
    public Integer getReviewsCount() { return reviewsCount; }
    public String getMaterialTone() { return materialTone; }
    public String getVibe() { return vibe; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<ProductOption> getOptions() { return options; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSlug(String slug) { this.slug = slug; }
    public void setCategorySlug(String categorySlug) { this.categorySlug = categorySlug; }
    public void setLevel(String level) { this.level = level; }
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }
    public void setDescription(String description) { this.description = description; }
    public void setDetailOne(String detailOne) { this.detailOne = detailOne; }
    public void setDetailTwo(String detailTwo) { this.detailTwo = detailTwo; }
    public void setDetailThree(String detailThree) { this.detailThree = detailThree; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setBestseller(Boolean bestseller) { this.bestseller = bestseller; }
    public void setNewIn(Boolean newIn) { this.newIn = newIn; }
    public void setFeatured(Boolean featured) { this.featured = featured; }
    public void setRating(Double rating) { this.rating = rating; }
    public void setReviewsCount(Integer reviewsCount) { this.reviewsCount = reviewsCount; }
    public void setMaterialTone(String materialTone) { this.materialTone = materialTone; }
    public void setVibe(String vibe) { this.vibe = vibe; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setOptions(List<ProductOption> options) { this.options = options; }
}