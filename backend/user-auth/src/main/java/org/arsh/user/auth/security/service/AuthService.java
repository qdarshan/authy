package org.arsh.user.auth.security.service;

import org.arsh.user.auth.dto.request.LoginUserDto;
import org.arsh.user.auth.dto.request.RegisterUserDto;
import org.arsh.user.auth.exception.UnAuthorizedException;
import org.arsh.user.auth.model.Role;
import org.arsh.user.auth.model.User;
import org.arsh.user.auth.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private AuthyUserDetailsService userDetailsService;
//    private final SecretGenerator  secretGenerator;
//    private final TotpManager totpManager;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public AuthService(JwtService jwtService, PasswordEncoder passwordEncoder, UserRepository userRepository, AuthenticationManager authenticationManager, AuthyUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    public User register(RegisterUserDto registerUserDto) {
        User user = new User();
        user.setEmail(registerUserDto.getEmail());
        user.setRoles(Set.of(Role.USER));
        user.setPassword(bCryptPasswordEncoder.encode(registerUserDto.getPassword()));
        return userRepository.save(user);
    }


    public Map<String, String> login(LoginUserDto loginUserDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPassword()));
        if (authentication.isAuthenticated()) {
            User user = new User();
            user.setEmail(loginUserDto.getEmail());

            String accessToken = jwtService.generateJwtToken(user);
            String refreshToken = jwtService.generateRefreshJwtToken(user);

            return Map.of(
                    "accessToken", accessToken,
                    "refreshToken", refreshToken
            );
        }
        throw new UnAuthorizedException("Invalid credentials");
    }

    public String refreshToken(String refreshToken) {
        if (!jwtService.isTokenExpired(refreshToken)) {
            String username = jwtService.getUserNameFromJwtToken(refreshToken);
            UserDetails user = userDetailsService.loadUserByUsername(username);
            String accessToken = jwtService.generateJwtToken(user);
            return accessToken;
        }
        throw new UnAuthorizedException("Refresh token is expired");
    }
}

