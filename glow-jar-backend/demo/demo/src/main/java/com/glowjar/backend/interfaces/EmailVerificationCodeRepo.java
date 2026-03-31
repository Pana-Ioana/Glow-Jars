package com.glowjar.backend.interfaces;

import com.glowjar.backend.entities.EmailVerificationCode;
import com.glowjar.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmailVerificationCodeRepo extends JpaRepository<EmailVerificationCode, Long> {
    Optional<EmailVerificationCode> findTopByUserAndTypeAndUsedFalseOrderByCreatedAtDesc(User user, String type);
    List<EmailVerificationCode> findByUserAndTypeAndUsedFalse(User user, String type);
}