package org.arsh.user.auth.validator.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.arsh.user.auth.dto.request.RegisterUserDto;
import org.arsh.user.auth.validator.PasswordMatches;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegisterUserDto> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(RegisterUserDto registerUserDto, ConstraintValidatorContext constraintValidatorContext) {
        final String password = registerUserDto.getPassword();
        final String confirmPassword = registerUserDto.getConfirmPassword();
        if (password == null || confirmPassword == null || !password.equals(confirmPassword)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Passwords don't match")
                    .addPropertyNode("matchingPassword")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
