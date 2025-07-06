package org.arsh.authy.web.controller;

import jakarta.validation.Valid;
import org.arsh.authy.common.model.Response;
import org.arsh.authy.core.service.AuthService;
import org.arsh.authy.web.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final String REGISTER_USER_SUCCESS_MSG = "Please check your email for a verification link.";
    private static final String LOGIN_USER_SUCCESS_MSG = "Successfully logged in.";
    private static final String EMAIL_VERIFICATION_SUCCESS_MSG = "Email verified successfully.";
    private static final String FORGOT_PASSWORD_SUCCESS_MSG = "A password reset link has been sent to your email, if an account exists.";
    private static final String RESET_PASSWORD_SUCCESS_MSG = "Password reset successfully.";

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Response<?>> registerUser(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.success(REGISTER_USER_SUCCESS_MSG));
    }

    @GetMapping("/verify-email")
    public ResponseEntity<Response<?>> verifyEmail(@RequestParam("token") String token) {
        authService.verifyEmail(token);
        return ResponseEntity.ok(Response.success(EMAIL_VERIFICATION_SUCCESS_MSG));
    }

    @PostMapping("/login")
    public ResponseEntity<Response<AuthResponse>> loginUser(@Valid @RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);
        return ResponseEntity.ok(Response.success(authResponse, LOGIN_USER_SUCCESS_MSG));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Response<?>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        authService.forgotPassword(request);
        return ResponseEntity.ok(Response.success(FORGOT_PASSWORD_SUCCESS_MSG));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Response<?>> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
        return ResponseEntity.ok(Response.success(RESET_PASSWORD_SUCCESS_MSG));
    }
}