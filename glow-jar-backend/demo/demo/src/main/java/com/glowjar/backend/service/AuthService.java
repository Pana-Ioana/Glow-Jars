package com.glowjar.backend.service;

import com.glowjar.backend.dto.*;
import com.glowjar.backend.entities.EmailVerificationCode;
import com.glowjar.backend.entities.User;
import com.glowjar.backend.interfaces.EmailVerificationCodeRepo;
import com.glowjar.backend.interfaces.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class AuthService {
    private static final String VERIFY_EMAIL = "VERIFY_EMAIL";
    private static final String RESET_PASSWORD = "RESET_PASSWORD";

    private final UserRepo userRepo;
    private final EmailVerificationCodeRepo emailVerificationCodeRepo;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthService(
            UserRepo userRepo,
            EmailVerificationCodeRepo emailVerificationCodeRepo,
            PasswordEncoder passwordEncoder,
            EmailService emailService
    ) {
        this.userRepo = userRepo;
        this.emailVerificationCodeRepo = emailVerificationCodeRepo;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public MessageResponse register(RegisterRequest request) {
        User existingUser = userRepo.findByEmail(request.getEmail()).orElse(null);

        if (existingUser != null) {
            if (Boolean.TRUE.equals(existingUser.getEnabled())) {
                throw new RuntimeException("Exista deja un cont cu acest email.");
            }

            invalidateOldCodes(existingUser, VERIFY_EMAIL);

            String newCode = generateVerificationCode();
            saveVerificationCode(existingUser, newCode, VERIFY_EMAIL);

            emailService.sendVerificationCode(
                    existingUser.getEmail(),
                    existingUser.getFirstName(),
                    newCode,
                    "Cod verificare Glow Jar",
                    "Foloseste acest cod pentru a-ti verifica adresa de email."
            );

            return new MessageResponse("Contul exista deja, dar nu este verificat. Am trimis un nou cod pe email.");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(false);

        User savedUser = userRepo.save(user);

        String code = generateVerificationCode();
        saveVerificationCode(savedUser, code, VERIFY_EMAIL);

        emailService.sendVerificationCode(
                savedUser.getEmail(),
                savedUser.getFirstName(),
                code,
                "Cod verificare Glow Jar",
                "Foloseste acest cod pentru a-ti verifica adresa de email."
        );

        return new MessageResponse("Cont creat cu succes. Ti-am trimis un cod de verificare pe email.");
    }

    public MessageResponse verifyEmail(VerifyEmailRequest request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Nu exista niciun cont cu acest email."));

        if (Boolean.TRUE.equals(user.getEnabled())) {
            return new MessageResponse("Emailul este deja verificat.");
        }

        EmailVerificationCode verificationCode = emailVerificationCodeRepo
                .findTopByUserAndTypeAndUsedFalseOrderByCreatedAtDesc(user, VERIFY_EMAIL)
                .orElseThrow(() -> new RuntimeException("Nu exista niciun cod activ pentru acest cont."));

        if (verificationCode.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Codul a expirat. Cere unul nou.");
        }

        if (!verificationCode.getCode().equals(request.getCode())) {
            throw new RuntimeException("Cod invalid.");
        }

        verificationCode.setUsed(true);
        emailVerificationCodeRepo.save(verificationCode);

        user.setEnabled(true);
        userRepo.save(user);

        return new MessageResponse("Email verificat cu succes. Acum te poti loga.");
    }

    public MessageResponse resendCode(ResendCodeRequest request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Nu exista niciun cont cu acest email."));

        if (Boolean.TRUE.equals(user.getEnabled())) {
            return new MessageResponse("Contul este deja verificat.");
        }

        invalidateOldCodes(user, VERIFY_EMAIL);

        String newCode = generateVerificationCode();
        saveVerificationCode(user, newCode, VERIFY_EMAIL);

        emailService.sendVerificationCode(
                user.getEmail(),
                user.getFirstName(),
                newCode,
                "Cod verificare Glow Jar",
                "Foloseste acest cod pentru a-ti verifica adresa de email."
        );

        return new MessageResponse("Am trimis un nou cod de verificare pe email.");
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email sau parola incorecta."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Email sau parola incorecta.");
        }

        if (!Boolean.TRUE.equals(user.getEnabled())) {
            throw new RuntimeException("Contul nu este verificat. Verifica emailul inainte de logare.");
        }

        return new AuthResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }

    public MessageResponse forgotPassword(ForgotPasswordRequest request) {
        User user = userRepo.findByEmail(request.getEmail()).orElse(null);

        if (user == null) {
            return new MessageResponse("Daca exista un cont cu acest email, am trimis un cod de resetare.");
        }

        invalidateOldCodes(user, RESET_PASSWORD);

        String code = generateVerificationCode();
        saveVerificationCode(user, code, RESET_PASSWORD);

        emailService.sendVerificationCode(
                user.getEmail(),
                user.getFirstName(),
                code,
                "Resetare parola Glow Jar",
                "Foloseste acest cod pentru a-ti reseta parola."
        );

        return new MessageResponse("Daca exista un cont cu acest email, am trimis un cod de resetare.");
    }

    public MessageResponse resetPassword(ResetPasswordRequest request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Nu exista niciun cont cu acest email."));

        EmailVerificationCode verificationCode = emailVerificationCodeRepo
                .findTopByUserAndTypeAndUsedFalseOrderByCreatedAtDesc(user, RESET_PASSWORD)
                .orElseThrow(() -> new RuntimeException("Nu exista niciun cod activ pentru resetarea parolei."));

        if (verificationCode.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Codul a expirat. Cere unul nou.");
        }

        if (!verificationCode.getCode().equals(request.getCode())) {
            throw new RuntimeException("Cod invalid.");
        }

        verificationCode.setUsed(true);
        emailVerificationCodeRepo.save(verificationCode);

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepo.save(user);

        return new MessageResponse("Parola a fost schimbata cu succes.");
    }

    private String generateVerificationCode() {
        int code = 100000 + new Random().nextInt(900000);
        return String.valueOf(code);
    }

    private void saveVerificationCode(User user, String code, String type) {
        EmailVerificationCode verificationCode = new EmailVerificationCode();
        verificationCode.setUser(user);
        verificationCode.setCode(code);
        verificationCode.setType(type);
        verificationCode.setExpiresAt(LocalDateTime.now().plusMinutes(10));
        verificationCode.setUsed(false);

        emailVerificationCodeRepo.save(verificationCode);
    }

    private void invalidateOldCodes(User user, String type) {
        List<EmailVerificationCode> activeCodes = emailVerificationCodeRepo.findByUserAndTypeAndUsedFalse(user, type);

        for (EmailVerificationCode code : activeCodes) {
            code.setUsed(true);
        }

        emailVerificationCodeRepo.saveAll(activeCodes);
    }
}