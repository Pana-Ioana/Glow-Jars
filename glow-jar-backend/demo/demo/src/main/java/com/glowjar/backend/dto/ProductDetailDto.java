package com.glowjar.backend.dto;

import java.util.List;

public class ProductDetailDto {
    private Long id;
    private String name;
    private String slug;
    private String categorySlug;
    private String level;
    private String shortDescription;
    private String description;
    private String detailOne;
    private String detailTwo;
    private String detailThree;
    private String imageUrl;
    private Boolean bestseller;
    private Boolean newIn;
    private Boolean featured;
    private Double rating;
    private Integer reviewsCount;
    private String materialTone;
    private String vibe;
    private List<ProductOptionDto> options;

    public ProductDetailDto(Long id, String name, String slug, String categorySlug, String level,
                            String shortDescription, String description,
                            String detailOne, String detailTwo, String detailThree,
                            String imageUrl, Boolean bestseller, Boolean newIn, Boolean featured,
                            Double rating, Integer reviewsCount, String materialTone, String vibe,
                            List<ProductOptionDto> options) {
        this.id = id;
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
        this.options = options;
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
    public List<ProductOptionDto> getOptions() { return options; }
}