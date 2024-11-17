package org.arsh.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.arsh.backend.validator.UsernameOrEmailRequired;

@Data
@UsernameOrEmailRequired
public class LoginUserDto {

    private String username;
    private String email;
    @NotBlank
    private String password;
}
