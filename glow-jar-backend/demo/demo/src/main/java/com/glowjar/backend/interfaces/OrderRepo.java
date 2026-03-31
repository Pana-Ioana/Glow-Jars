package com.glowjar.backend.interfaces;

import com.glowjar.backend.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findByEmailOrderByCreatedAtDesc(String email);
}