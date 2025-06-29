package org.arsh.authy.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotNull(message = "Email cannot be empty")
        @Email(message = "Please provide a valid email address")
        String email,

        @NotNull(message = "Password cannot be empty")
        @Size(min = 8, max = 50, message = "Password must be between 8 to 50 characters")
        String password
) {
}
