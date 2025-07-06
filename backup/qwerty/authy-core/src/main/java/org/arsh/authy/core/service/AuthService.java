package org.arsh.authy.core.service;

import org.arsh.authy.web.dto.AuthResponse;
import org.arsh.authy.web.dto.LoginRequest;
import org.arsh.authy.web.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
