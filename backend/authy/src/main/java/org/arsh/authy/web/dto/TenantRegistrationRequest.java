package org.arsh.authy.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TenantRegistrationRequest(
        @NotBlank(message = "Tenant identifier cannot be blank")
        @Size(min = 3, max = 30, message = "Identifier must be between 3 and 30 characters")
        @Pattern(regexp = "^[a-z0-9-]+$", message = "Identifier can only contain lowercase letters, numbers, and hyphens")
        String tenantId,

        @NotBlank(message = "Tenant name cannot be blank")
        @Size(min = 2, max = 100)
        String name
) {
}
