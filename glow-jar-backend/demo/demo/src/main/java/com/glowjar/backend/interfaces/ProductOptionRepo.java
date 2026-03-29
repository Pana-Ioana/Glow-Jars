package com.glowjar.backend.interfaces;

import com.glowjar.backend.entities.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepo extends JpaRepository<ProductOption, Long> {
}