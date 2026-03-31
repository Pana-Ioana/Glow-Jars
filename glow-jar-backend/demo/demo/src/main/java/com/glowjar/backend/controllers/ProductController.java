package com.glowjar.backend.controllers;

import com.glowjar.backend.dto.ProductDetailDto;
import com.glowjar.backend.dto.ProductListItemDto;
import com.glowjar.backend.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductListItemDto> getProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String level,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer minPieces,
            @RequestParam(required = false) Integer maxPieces,
            @RequestParam(required = false) String materialTone,
            @RequestParam(required = false) String vibe,
            @RequestParam(required = false) Boolean bestseller,
            @RequestParam(required = false) Boolean newIn,
            @RequestParam(required = false) Boolean availableOnly,
            @RequestParam(required = false) String sort
    ) {
        return productService.getProducts(
                category, level, search, size,
                minPrice, maxPrice, minPieces, maxPieces,
                materialTone, vibe, bestseller, newIn, availableOnly, sort
        );
    }

    @GetMapping("/{slug}")
    public ProductDetailDto getProductBySlug(@PathVariable String slug) {
        return productService.getProductBySlug(slug);
    }
}