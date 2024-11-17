package org.arsh.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.arsh.backend.validator.PasswordMatches;
import org.arsh.backend.validator.ValidEmail;
import org.arsh.backend.validator.ValidPassword;

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
