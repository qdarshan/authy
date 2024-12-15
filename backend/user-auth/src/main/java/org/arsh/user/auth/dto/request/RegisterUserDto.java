package org.arsh.user.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.arsh.user.auth.validator.PasswordMatches;
import org.arsh.user.auth.validator.ValidEmail;
import org.arsh.user.auth.validator.ValidPassword;

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
