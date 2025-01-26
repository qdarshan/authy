package org.arsh.auth.core.service.validator.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.arsh.auth.core.service.validator.ValidEmail;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    @Override
    public void initialize(ValidEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Email cannot be null")
                    .addConstraintViolation();
            return false;
        }

        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!java.util.regex.Pattern.compile(regex).matcher(email).matches()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid email")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}