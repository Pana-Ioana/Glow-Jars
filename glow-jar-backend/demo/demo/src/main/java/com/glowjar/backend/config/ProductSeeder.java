package com.glowjar.backend.config;

import com.glowjar.backend.entities.Product;
import com.glowjar.backend.entities.ProductOption;
import com.glowjar.backend.interfaces.ProductRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class ProductSeeder {

    private static final String FALLBACK_IMAGE =
            "https://images.unsplash.com/photo-1515562141207-7a88fb7ce338?auto=format&fit=crop&w=900&q=80";

    private static final Map<String, List<String>> CATEGORY_IMAGES = Map.ofEntries(
            Map.entry("soft-girl", List.of(
                    "https://images.unsplash.com/photo-1617038220319-276d3cfab638?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1515377905703-c4788e51af15?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1521572267360-ee0c2909d518?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1515886657613-9f3515b0c78f?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1524504388940-b1c1722653e1?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1522312346375-d1a52e2b99b3?auto=format&fit=crop&w=900&q=80"
            )),
            Map.entry("golden-muse", List.of(
                    "https://images.unsplash.com/photo-1611652022419-460bfbe1220a?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1617038260897-41a1f14a8ca0?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1612817159949-195b6eb9e31a?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1523170335258-f5ed11844a49?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1611923134239-b9be5816c2d5?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1506630448388-4e683c67ddb0?auto=format&fit=crop&w=900&q=80"
            )),
            Map.entry("minimal-glow", List.of(
                    "https://images.unsplash.com/photo-1573408301185-9146fe634ad0?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1523170335258-f5ed11844a49?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1611923134239-b9be5816c2d5?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1612817159949-195b6eb9e31a?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1611652022419-460bfbe1220a?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1515562141207-7a88fb7ce338?auto=format&fit=crop&w=900&q=80"
            )),
            Map.entry("dark-romance", List.of(
                    "https://images.unsplash.com/photo-1535632066927-ab7c9ab60908?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1611107683227-e9060eccd846?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1529139574466-a303027c1d8b?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1503341455253-b2e723bb3dbb?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1611923134239-b9be5816c2d5?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1515562141207-7a88fb7ce338?auto=format&fit=crop&w=900&q=80"
            )),
            Map.entry("rose-quartz", List.of(
                    "https://images.unsplash.com/photo-1515562141207-7a88fb7ce338?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1617038220319-276d3cfab638?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1522312346375-d1a52e2b99b3?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1515886657613-9f3515b0c78f?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1524504388940-b1c1722653e1?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1521572267360-ee0c2909d518?auto=format&fit=crop&w=900&q=80"
            )),
            Map.entry("celestial-glow", List.of(
                    "https://images.unsplash.com/photo-1515562141207-7a88fb7ce338?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1521572267360-ee0c2909d518?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1503341455253-b2e723bb3dbb?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1529139574466-a303027c1d8b?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1611107683227-e9060eccd846?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1611923134239-b9be5816c2d5?auto=format&fit=crop&w=900&q=80"
            )),
            Map.entry("vintage-muse", List.of(
                    "https://images.unsplash.com/photo-1611652022419-460bfbe1220a?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1523170335258-f5ed11844a49?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1612817159949-195b6eb9e31a?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1573408301185-9146fe634ad0?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1515562141207-7a88fb7ce338?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1617038260897-41a1f14a8ca0?auto=format&fit=crop&w=900&q=80"
            )),
            Map.entry("cottage-bloom", List.of(
                    "https://images.unsplash.com/photo-1524504388940-b1c1722653e1?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1522312346375-d1a52e2b99b3?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1515886657613-9f3515b0c78f?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1521572267360-ee0c2909d518?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1515377905703-c4788e51af15?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1506630448388-4e683c67ddb0?auto=format&fit=crop&w=900&q=80"
            )),
            Map.entry("edgy-chic", List.of(
                    "https://images.unsplash.com/photo-1535632066927-ab7c9ab60908?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1611107683227-e9060eccd846?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1529139574466-a303027c1d8b?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1503341455253-b2e723bb3dbb?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1611923134239-b9be5816c2d5?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1515562141207-7a88fb7ce338?auto=format&fit=crop&w=900&q=80"
            )),
            Map.entry("boho-dream", List.of(
                    "https://images.unsplash.com/photo-1611107683227-e9060eccd846?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1506630448388-4e683c67ddb0?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1524504388940-b1c1722653e1?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1515886657613-9f3515b0c78f?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1521572267360-ee0c2909d518?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1522312346375-d1a52e2b99b3?auto=format&fit=crop&w=900&q=80"
            )),
            Map.entry("glam-goddess", List.of(
                    "https://images.unsplash.com/photo-1611652022419-460bfbe1220a?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1617038260897-41a1f14a8ca0?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1612817159949-195b6eb9e31a?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1523170335258-f5ed11844a49?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1611923134239-b9be5816c2d5?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1515562141207-7a88fb7ce338?auto=format&fit=crop&w=900&q=80"
            ))
    );

    @Bean
    CommandLineRunner seedProducts(ProductRepo productRepo) {
        return args -> {
            List<CategorySeed> categories = List.of(
                    new CategorySeed("Soft Girl", "soft-girl", "delicat, feminin si dreamy", "soft", "gold"),
                    new CategorySeed("Golden Muse", "golden-muse", "luminos, elegant si sofisticat", "glam", "gold"),
                    new CategorySeed("Minimal Glow", "minimal-glow", "curat, modern si effortless", "minimal", "silver"),
                    new CategorySeed("Dark Romance", "dark-romance", "dramatic, magnetic si intens", "dramatic", "silver"),
                    new CategorySeed("Rose Quartz", "rose-quartz", "romantic, fin si glossy", "romantic", "gold"),
                    new CategorySeed("Celestial Glow", "celestial-glow", "visator, astral si luminos", "celestial", "silver"),
                    new CategorySeed("Vintage Muse", "vintage-muse", "retro, elegant si timeless", "vintage", "mixed"),
                    new CategorySeed("Cottage Bloom", "cottage-bloom", "natural, floral si soft", "cottage", "gold"),
                    new CategorySeed("Edgy Chic", "edgy-chic", "indraznet, urban si modern", "edgy", "silver"),
                    new CategorySeed("Boho Dream", "boho-dream", "airy, relaxed si liber", "boho", "mixed"),
                    new CategorySeed("Glam Goddess", "glam-goddess", "stralucitor, opulent si statement", "glam", "gold")
            );

            List<VariantSeed> variants = List.of(
                    new VariantSeed("Essential", "essential", 3, 5, 7, 89.0, 119.0, 149.0, true, true, false),
                    new VariantSeed("Signature", "signature", 4, 6, 8, 109.0, 139.0, 179.0, true, false, true),
                    new VariantSeed("Luxe", "luxe", 4, 6, 9, 129.0, 169.0, 209.0, true, true, true),
                    new VariantSeed("Boutique", "boutique", 3, 5, 8, 99.0, 129.0, 165.0, false, true, true),
                    new VariantSeed("Dream", "dream", 4, 6, 8, 105.0, 135.0, 175.0, false, false, true),
                    new VariantSeed("Premium", "premium", 5, 7, 9, 139.0, 179.0, 219.0, true, false, true),
                    new VariantSeed("Deluxe", "deluxe", 4, 6, 9, 119.0, 159.0, 199.0, false, true, false),
                    new VariantSeed("Edit", "edit", 3, 5, 7, 95.0, 125.0, 155.0, false, false, true),
                    new VariantSeed("Atelier", "atelier", 5, 7, 10, 145.0, 185.0, 229.0, true, true, true),
                    new VariantSeed("Collector", "collector", 6, 8, 11, 159.0, 199.0, 249.0, true, false, false)
            );

            int index = 0;

            for (CategorySeed category : categories) {
                for (VariantSeed variant : variants) {
                    index++;

                    String name = category.name() + " " + variant.name();
                    String slug = category.slug() + "-" + variant.slug();

                    double rating = 4.5 + (index % 5) * 0.1;
                    int reviewsCount = 12 + (index * 3);

                    String shortDescription = "Un glow jar " + category.styleText() + ".";
                    String description = "Acest Glow Jar este realizat la comanda si aduce estetica "
                            + category.name()
                            + " intr-o selectie atent curatoriata, cu piese feminine, purtabile si cu vibe boutique.";

                    String detailOne = "Realizat la comanda";
                    String detailTwo = "Selectie inspirata de estetica " + category.name();
                    String detailThree = "Disponibil in 3 marimi de borcan";

                    seedProduct(
                            productRepo,
                            name,
                            slug,
                            category.slug(),
                            variant.slug(),
                            shortDescription,
                            description,
                            detailOne,
                            detailTwo,
                            detailThree,
                            getRandomImageUrlForCategory(category.slug()),
                            variant.bestseller(),
                            variant.newIn(),
                            variant.featured(),
                            rating,
                            reviewsCount,
                            category.materialTone(),
                            category.vibe(),
                            variant.smallPieces(),
                            variant.smallPrice(),
                            variant.mediumPieces(),
                            variant.mediumPrice(),
                            variant.largePieces(),
                            variant.largePrice()
                    );
                }
            }
        };
    }

    private void seedProduct(
            ProductRepo repo,
            String name,
            String slug,
            String categorySlug,
            String level,
            String shortDescription,
            String description,
            String detailOne,
            String detailTwo,
            String detailThree,
            String imageUrl,
            Boolean bestseller,
            Boolean newIn,
            Boolean featured,
            Double rating,
            Integer reviewsCount,
            String materialTone,
            String vibe,
            Integer smallPieces,
            Double smallPrice,
            Integer mediumPieces,
            Double mediumPrice,
            Integer largePieces,
            Double largePrice
    ) {
        if (repo.existsBySlug(slug)) {
            return;
        }

        Product product = new Product(
                name,
                slug,
                categorySlug,
                level,
                shortDescription,
                description,
                detailOne,
                detailTwo,
                detailThree,
                imageUrl,
                bestseller,
                newIn,
                featured,
                rating,
                reviewsCount,
                materialTone,
                vibe
        );

        product.addOption(new ProductOption("small", smallPieces, smallPrice, true));
        product.addOption(new ProductOption("medium", mediumPieces, mediumPrice, true));
        product.addOption(new ProductOption("large", largePieces, largePrice, true));

        repo.save(product);
    }

    private String getRandomImageUrlForCategory(String categorySlug) {
        List<String> images = CATEGORY_IMAGES.get(categorySlug);

        if (images == null || images.isEmpty()) {
            return FALLBACK_IMAGE;
        }

        int index = ThreadLocalRandom.current().nextInt(images.size());
        String selected = images.get(index);

        if (selected == null || selected.isBlank()) {
            return FALLBACK_IMAGE;
        }

        return selected;
    }

    private record CategorySeed(
            String name,
            String slug,
            String styleText,
            String vibe,
            String materialTone
    ) {}

    private record VariantSeed(
            String name,
            String slug,
            int smallPieces,
            int mediumPieces,
            int largePieces,
            double smallPrice,
            double mediumPrice,
            double largePrice,
            boolean bestseller,
            boolean newIn,
            boolean featured
    ) {}
}