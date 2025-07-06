package org.arsh.auth.core.service.validator.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.arsh.auth.core.model.dto.request.LoginUserDto;
import org.arsh.auth.core.service.validator.UsernameOrEmailRequired;

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
