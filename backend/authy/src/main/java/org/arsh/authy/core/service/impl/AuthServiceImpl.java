package org.arsh.authy.core.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.arsh.authy.core.context.TenantContext;
import org.arsh.authy.core.service.AuthService;
import org.arsh.authy.persistence.entity.*;
import org.arsh.authy.persistence.repository.EmailVerificationTokenRepository;
import org.arsh.authy.persistence.repository.PasswordResetTokenRepository;
import org.arsh.authy.persistence.repository.TenantRepository;
import org.arsh.authy.persistence.repository.UserRepository;
import org.arsh.authy.security.service.JwtService;
import org.arsh.authy.web.dto.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(
            UserRepository userRepository,
            TenantRepository tenantRepository,
            PasswordResetTokenRepository passwordResetTokenRepository,
            EmailVerificationTokenRepository emailVerificationTokenRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void register(
            RegisterRequest request
    ) {
        Long tenantId = TenantContext.getCurrentTenant();
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant context not set for registration");
        }

        TenantEntity currentTenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found for ID: " + tenantId));

        if (userRepository.existsByEmailAndTenant_Id(request.email(), tenantId)) {
            throw new IllegalArgumentException("Email is already in use");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(request.email());
        userEntity.setPasswordHash(passwordEncoder.encode(request.password()));
        userEntity.setRole(Role.USER);
        userEntity.setTenant(currentTenant);
        userEntity.setEnabled(false);
        userRepository.save(userEntity);

        String token = UUID.randomUUID().toString();
        EmailVerificationTokenEntity emailVerificationToken = new EmailVerificationTokenEntity(token, userEntity);
        emailVerificationTokenRepository.save(emailVerificationToken);
        log.info("Verification email for {}. Token: {}", userEntity.getEmail(), token);
    }

    @Override
    public void verifyEmail(
            String token
    ) {
        Long tenantId = TenantContext.getCurrentTenant();
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant context not set for email verification");
        }
        EmailVerificationTokenEntity emailVerificationToken = emailVerificationTokenRepository.findByToken(token)
                .orElseThrow(() -> {
                    log.warn("Invalid token used for email verification: {}", token);
                    return new IllegalArgumentException("Invalid verification token.");
                });

        if (emailVerificationToken.isExpired()) {
            emailVerificationTokenRepository.delete(emailVerificationToken);
            log.info("Expired token used for email verification: {}", token);
            throw new IllegalArgumentException("Invalid or expired email verification token");
        }

        UserEntity user = emailVerificationToken.getUser();
        if (!user.getTenant().getId().equals(tenantId)) {
            log.error("SECURITY ALERT: Tenant mismatch during email verification. Context Tenant: {}, User's Tenant: {}. Token: {}",
                    tenantId, user.getTenant().getId(), token);
            throw new IllegalArgumentException("Invalid or expired email verification token.");
        }
        user.setEnabled(true);
        userRepository.save(user);
        emailVerificationTokenRepository.delete(emailVerificationToken);
    }

    @Override
    public AuthResponse login(
            LoginRequest request
    ) {
        Long tenantId = TenantContext.getCurrentTenant();
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant context not set for registration");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.email(), request.password()
        ));

        UserEntity userEntity = userRepository.findByEmailAndTenant_Id(request.email(), tenantId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found after authentication"));

        String token = jwtService.generateToken(userEntity);
        return new AuthResponse(token);
    }

    @Override
    public void forgotPassword(
            ForgotPasswordRequest request
    ) {
        Long tenantId = TenantContext.getCurrentTenant();
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant context not set for forgot password");
        }

        userRepository.findByEmailAndTenant_Id(request.email(), tenantId).ifPresent(user -> {
            String token = UUID.randomUUID().toString();
            PasswordResetTokenEntity passwordResetToken = new PasswordResetTokenEntity(token, user);
            passwordResetTokenRepository.save(passwordResetToken);
            log.info("Generated password reset token for user {}. Token: {}", user.getEmail(), token);
        });
    }

    @Override
    @Transactional
    public void resetPassword(
            ResetPasswordRequest request
    ) {
        Long tenantId = TenantContext.getCurrentTenant();
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant context not set for forgot password");
        }

        PasswordResetTokenEntity passwordResetToken = passwordResetTokenRepository.findByToken(request.token())
                .orElseThrow(() -> {
                    log.warn("Invalid token used for password reset: {}", request.token());
                    return new IllegalArgumentException("Invalid or expired password reset token.");
                });

        if (passwordResetToken.isExpired()) {
            passwordResetTokenRepository.delete(passwordResetToken);
            log.info("Expired token used for password reset: {}", request.token());
            throw new IllegalArgumentException("Invalid or expired password reset token.");
        }

        UserEntity user = passwordResetToken.getUser();
        if (!user.getTenant().getId().equals(tenantId)) {
            log.error("SECURITY ALERT: Tenant mismatch during password reset. Context Tenant: {}, User's Tenant: {}. Token: {}",
                    tenantId, user.getTenant().getId(), request.token());
            throw new IllegalArgumentException("Invalid or expired password reset token.");
        }

        user.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
        passwordResetTokenRepository.delete(passwordResetToken);
        log.info("Password reset successfully for user: {}", user.getId());
    }
}
