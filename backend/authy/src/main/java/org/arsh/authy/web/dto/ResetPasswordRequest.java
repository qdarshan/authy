package org.arsh.authy.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(
        @NotNull(message = "Token cannot be empty")
        String token,

        @NotNull(message = "Password cannot be empty")
        @Size(min = 8, max = 50, message = "Password must be between 8 to 50 characters")
        String newPassword
) {
}
