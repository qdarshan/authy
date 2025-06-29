package org.arsh.authy.core.service.impl;

import org.arsh.authy.core.service.AuthService;
import org.arsh.authy.persistence.entity.Role;
import org.arsh.authy.persistence.entity.UserEntity;
import org.arsh.authy.persistence.repository.UserRepository;
import org.arsh.authy.security.service.JwtService;
import org.arsh.authy.web.dto.AuthResponse;
import org.arsh.authy.web.dto.LoginRequest;
import org.arsh.authy.web.dto.RegisterRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponse register(
            RegisterRequest request
    ) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email is already in use");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(request.email());
        userEntity.setPasswordHash(passwordEncoder.encode(request.password()));
        userEntity.setRole(Role.USER);
        userRepository.save(userEntity);
        return new AuthResponse("saved");
    }

    @Override
    public AuthResponse login(
            LoginRequest request
    ) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.email(), request.password()
        ));

        UserEntity userEntity = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found after authentication"));

        String token = jwtService.generateToken(userEntity);
        return new AuthResponse(token);
    }
}
