package org.arsh.auth.core.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.arsh.auth.core.service.validator.UsernameOrEmailRequired;

@Data
@UsernameOrEmailRequired
public class LoginUserDto {

    private String username;
    private String email;
    @NotBlank
    private String password;
}
