package com.glowjar.backend.dto;

public class ProductListItemDto {
    private Long id;
    private String name;
    private String slug;
    private String categorySlug;
    private String level;
    private String shortDescription;
    private String imageUrl;
    private Boolean bestseller;
    private Boolean newIn;
    private Double rating;
    private Integer reviewsCount;
    private String materialTone;
    private String vibe;
    private Double minPrice;
    private Integer minPieces;

    public ProductListItemDto(Long id, String name, String slug, String categorySlug, String level,
                              String shortDescription, String imageUrl, Boolean bestseller, Boolean newIn,
                              Double rating, Integer reviewsCount, String materialTone, String vibe,
                              Double minPrice, Integer minPieces) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.categorySlug = categorySlug;
        this.level = level;
        this.shortDescription = shortDescription;
        this.imageUrl = imageUrl;
        this.bestseller = bestseller;
        this.newIn = newIn;
        this.rating = rating;
        this.reviewsCount = reviewsCount;
        this.materialTone = materialTone;
        this.vibe = vibe;
        this.minPrice = minPrice;
        this.minPieces = minPieces;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSlug() { return slug; }
    public String getCategorySlug() { return categorySlug; }
    public String getLevel() { return level; }
    public String getShortDescription() { return shortDescription; }
    public String getImageUrl() { return imageUrl; }
    public Boolean getBestseller() { return bestseller; }
    public Boolean getNewIn() { return newIn; }
    public Double getRating() { return rating; }
    public Integer getReviewsCount() { return reviewsCount; }
    public String getMaterialTone() { return materialTone; }
    public String getVibe() { return vibe; }
    public Double getMinPrice() { return minPrice; }
    public Integer getMinPieces() { return minPieces; }
}