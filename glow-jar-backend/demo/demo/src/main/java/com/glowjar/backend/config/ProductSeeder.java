package com.glowjar.backend.config;

import com.glowjar.backend.entities.Product;
import com.glowjar.backend.entities.ProductOption;
import com.glowjar.backend.interfaces.ProductRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ProductSeeder {

    @Bean
    CommandLineRunner seedProducts(ProductRepo productRepo) {
        return args -> {
            List<CategorySeed> categories = List.of(
                    new CategorySeed("Soft Girl", "soft-girl", "delicat, feminin si dreamy", "soft", "gold",
                            "https://images.unsplash.com/photo-1617038220319-276d3cfab638?auto=format&fit=crop&w=900&q=80"),

                    new CategorySeed("Golden Muse", "golden-muse", "luminos, elegant si sofisticat", "glam", "gold",
                            "https://images.unsplash.com/photo-1611652022419-460bfbe1220a?auto=format&fit=crop&w=900&q=80"),

                    new CategorySeed("Dark Romance", "dark-romance", "dramatic, magnetic si intens", "dramatic", "silver",
                            "https://images.unsplash.com/photo-1535632066927-ab7c9ab60908?auto=format&fit=crop&w=900&q=80"),

                    new CategorySeed("Boho Dream", "boho-dream", "airy, relaxed si liber", "boho", "mixed",
                            "https://images.unsplash.com/photo-1611107683227-e9060eccd846?auto=format&fit=crop&w=900&q=80"),

                    new CategorySeed("Minimal Glow", "minimal-glow", "curat, modern si effortless", "minimal", "silver",
                            "https://images.unsplash.com/photo-1573408301185-9146fe634ad0?auto=format&fit=crop&w=900&q=80"),

                    new CategorySeed("Rose Quartz", "rose-quartz", "romantic, fin si glossy", "romantic", "gold",
                            "https://images.unsplash.com/photo-1611591437281-460bfbe1220a?auto=format&fit=crop&w=900&q=80"),

                    new CategorySeed("Celestial Glow", "celestial-glow", "visator, astral si luminos", "celestial", "silver",
                            "https://images.unsplash.com/photo-1515562141207-7a88fb7ce338?auto=format&fit=crop&w=900&q=80"),

                    new CategorySeed("Coquette Charm", "coquette-charm", "playful, dulce si flirty", "coquette", "gold",
                            "https://images.unsplash.com/photo-1617038220319-276d3cfab638?auto=format&fit=crop&w=900&q=80"),

                    new CategorySeed("Moonlight Muse", "moonlight-muse", "misterios, rafinat si feminin", "mystic", "silver",
                            "https://images.unsplash.com/photo-1611107683227-e9060eccd846?auto=format&fit=crop&w=900&q=80"),

                    new CategorySeed("Pearl Edit", "pearl-edit", "clasic, chic si timeless", "classic", "mixed",
                            "https://plus.unsplash.com/premium_photo-1681276168324-a6f1958aa191?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8cGVhcmwlMjBqZXdlbHJ5fGVufDB8fDB8fHww")
            );

            List<VariantSeed> variants = List.of(
                    new VariantSeed("Essential", "essential", 0, 3, 5, 7, 89.0, 119.0, 149.0, true, true, false),
                    new VariantSeed("Signature", "signature", 1, 4, 6, 8, 109.0, 139.0, 179.0, true, false, true),
                    new VariantSeed("Luxe", "luxe", 2, 4, 6, 9, 129.0, 169.0, 209.0, true, true, true),
                    new VariantSeed("Boutique", "boutique", 3, 3, 5, 8, 99.0, 129.0, 165.0, false, true, true),
                    new VariantSeed("Dream", "dream", 4, 4, 6, 8, 105.0, 135.0, 175.0, false, false, true),
                    new VariantSeed("Premium", "premium", 5, 5, 7, 9, 139.0, 179.0, 219.0, true, false, true),
                    new VariantSeed("Deluxe", "deluxe", 6, 4, 6, 9, 119.0, 159.0, 199.0, false, true, false),
                    new VariantSeed("Edit", "edit", 7, 3, 5, 7, 95.0, 125.0, 155.0, false, false, true),
                    new VariantSeed("Atelier", "atelier", 8, 5, 7, 10, 145.0, 185.0, 229.0, true, true, true),
                    new VariantSeed("Collector", "collector", 9, 6, 8, 11, 159.0, 199.0, 249.0, true, false, false)
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
                            category.imageUrl(),
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
                name, slug, categorySlug, level,
                shortDescription, description,
                detailOne, detailTwo, detailThree,
                imageUrl, bestseller, newIn, featured,
                rating, reviewsCount, materialTone, vibe
        );

        product.addOption(new ProductOption("small", smallPieces, smallPrice, true));
        product.addOption(new ProductOption("medium", mediumPieces, mediumPrice, true));
        product.addOption(new ProductOption("large", largePieces, largePrice, true));

        repo.save(product);
    }

    private record CategorySeed(
            String name,
            String slug,
            String styleText,
            String vibe,
            String materialTone,
            String imageUrl
    ) {}

    private record VariantSeed(
            String name,
            String slug,
            int order,
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