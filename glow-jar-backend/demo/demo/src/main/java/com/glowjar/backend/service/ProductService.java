package com.glowjar.backend.service;

import com.glowjar.backend.dto.ProductDetailDto;
import com.glowjar.backend.dto.ProductListItemDto;
import com.glowjar.backend.dto.ProductOptionDto;
import com.glowjar.backend.entities.Product;
import com.glowjar.backend.entities.ProductOption;
import com.glowjar.backend.interfaces.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<ProductListItemDto> getProducts(
            String category,
            String level,
            String search,
            String size,
            Double minPrice,
            Double maxPrice,
            Integer minPieces,
            Integer maxPieces,
            String materialTone,
            String vibe,
            Boolean bestseller,
            Boolean newIn,
            Boolean availableOnly,
            String sort
    ) {
        List<Product> products = productRepo.findAll();

        return products.stream()
                .filter(p -> category == null || p.getCategorySlug().equalsIgnoreCase(category))
                .filter(p -> level == null || p.getLevel().equalsIgnoreCase(level))
                .filter(p -> search == null || matchesSearch(p, search))
                .filter(p -> materialTone == null || p.getMaterialTone().equalsIgnoreCase(materialTone))
                .filter(p -> vibe == null || p.getVibe().equalsIgnoreCase(vibe))
                .filter(p -> bestseller == null || p.getBestseller().equals(bestseller))
                .filter(p -> newIn == null || p.getNewIn().equals(newIn))
                .filter(p -> size == null || hasSize(p, size))
                .filter(p -> minPrice == null || hasPriceAtLeast(p, minPrice))
                .filter(p -> maxPrice == null || hasPriceAtMost(p, maxPrice))
                .filter(p -> minPieces == null || hasPiecesAtLeast(p, minPieces))
                .filter(p -> maxPieces == null || hasPiecesAtMost(p, maxPieces))
                .filter(p -> availableOnly == null || !availableOnly || hasAvailableOption(p))
                .sorted(buildComparator(sort))
                .map(this::toListItemDto)
                .collect(Collectors.toList());
    }

    public ProductDetailDto getProductBySlug(String slug) {
        Product product = productRepo.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<ProductOptionDto> optionDtos = product.getOptions().stream()
                .map(o -> new ProductOptionDto(
                        o.getId(),
                        o.getSize(),
                        o.getPiecesCount(),
                        o.getPrice(),
                        o.getAvailable()
                ))
                .collect(Collectors.toList());

        return new ProductDetailDto(
                product.getId(),
                product.getName(),
                product.getSlug(),
                product.getCategorySlug(),
                product.getLevel(),
                product.getShortDescription(),
                product.getDescription(),
                product.getDetailOne(),
                product.getDetailTwo(),
                product.getDetailThree(),
                product.getImageUrl(),
                product.getBestseller(),
                product.getNewIn(),
                product.getFeatured(),
                product.getRating(),
                product.getReviewsCount(),
                product.getMaterialTone(),
                product.getVibe(),
                optionDtos
        );
    }

    private boolean matchesSearch(Product p, String search) {
        String s = search.toLowerCase(Locale.ROOT);
        return p.getName().toLowerCase(Locale.ROOT).contains(s)
                || p.getCategorySlug().toLowerCase(Locale.ROOT).contains(s)
                || p.getLevel().toLowerCase(Locale.ROOT).contains(s)
                || (p.getShortDescription() != null && p.getShortDescription().toLowerCase(Locale.ROOT).contains(s));
    }

    private boolean hasSize(Product p, String size) {
        return p.getOptions().stream().anyMatch(o -> o.getSize().equalsIgnoreCase(size));
    }

    private boolean hasPriceAtLeast(Product p, Double minPrice) {
        return p.getOptions().stream().anyMatch(o -> o.getPrice() >= minPrice);
    }

    private boolean hasPriceAtMost(Product p, Double maxPrice) {
        return p.getOptions().stream().anyMatch(o -> o.getPrice() <= maxPrice);
    }

    private boolean hasPiecesAtLeast(Product p, Integer minPieces) {
        return p.getOptions().stream().anyMatch(o -> o.getPiecesCount() >= minPieces);
    }

    private boolean hasPiecesAtMost(Product p, Integer maxPieces) {
        return p.getOptions().stream().anyMatch(o -> o.getPiecesCount() <= maxPieces);
    }

    private boolean hasAvailableOption(Product p) {
        return p.getOptions().stream().anyMatch(ProductOption::getAvailable);
    }

    private ProductListItemDto toListItemDto(Product p) {
        Double minPrice = p.getOptions().stream()
                .map(ProductOption::getPrice)
                .min(Double::compareTo)
                .orElse(0.0);

        Integer minPieces = p.getOptions().stream()
                .map(ProductOption::getPiecesCount)
                .min(Integer::compareTo)
                .orElse(0);

        return new ProductListItemDto(
                p.getId(),
                p.getName(),
                p.getSlug(),
                p.getCategorySlug(),
                p.getLevel(),
                p.getShortDescription(),
                p.getImageUrl(),
                p.getBestseller(),
                p.getNewIn(),
                p.getRating(),
                p.getReviewsCount(),
                p.getMaterialTone(),
                p.getVibe(),
                minPrice,
                minPieces
        );
    }

    private Comparator<Product> buildComparator(String sort) {
        if (sort == null) {
            return Comparator.comparing(Product::getCreatedAt).reversed();
        }

        return switch (sort) {
            case "priceAsc" -> Comparator.comparing(this::extractMinPrice);
            case "priceDesc" -> Comparator.comparing(this::extractMinPrice).reversed();
            case "ratingDesc" -> Comparator.comparing(Product::getRating).reversed();
            case "nameAsc" -> Comparator.comparing(Product::getName);
            case "newest" -> Comparator.comparing(Product::getCreatedAt).reversed();
            default -> Comparator.comparing(Product::getCreatedAt).reversed();
        };
    }

    private Double extractMinPrice(Product p) {
        return p.getOptions().stream()
                .map(ProductOption::getPrice)
                .min(Double::compareTo)
                .orElse(0.0);
    }
}