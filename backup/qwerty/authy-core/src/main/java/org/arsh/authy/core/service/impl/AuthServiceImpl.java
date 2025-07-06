package org.arsh.authy.core.service.impl;

import org.arsh.authy.core.service.AuthService;
import org.arsh.authy.web.dto.AuthResponse;
import org.arsh.authy.web.dto.LoginRequest;
import org.arsh.authy.web.dto.RegisterRequest;

public class AuthServiceImpl implements AuthService {
    @Override
    public AuthResponse register(RegisterRequest request) {
        return new AuthResponse("hello");
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        return new AuthResponse("login");
    }
}
