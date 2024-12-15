package org.arsh.user.auth.validator.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.arsh.user.auth.dto.request.LoginUserDto;
import org.arsh.user.auth.validator.UsernameOrEmailRequired;

public class UsernameOrEmailRequiredValidator implements ConstraintValidator<UsernameOrEmailRequired, LoginUserDto> {
    @Override
    public void initialize(UsernameOrEmailRequired constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LoginUserDto loginUserDto, ConstraintValidatorContext constraintValidatorContext) {

        if (loginUserDto.getUsername() == null && loginUserDto.getEmail() == null) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Either username or email is required")
                    .addPropertyNode("usernameOrEmailRequired")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
