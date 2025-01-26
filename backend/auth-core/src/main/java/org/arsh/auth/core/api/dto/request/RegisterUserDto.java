package org.arsh.auth.core.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.arsh.auth.core.service.validator.PasswordMatches;
import org.arsh.auth.core.service.validator.ValidEmail;
import org.arsh.auth.core.service.validator.ValidPassword;

@Data
@PasswordMatches
public class RegisterUserDto {

    @NotBlank
    private String firstname;
    private String lastname;

    @ValidEmail
    private String email;

    @NotBlank
    private String username;

    @ValidPassword
    private String password;

    @ValidPassword
    private String confirmPassword;

}
