package org.arsh.user.auth.validator.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.arsh.user.auth.validator.ValidPassword;


public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password cannot be null")
                    .addConstraintViolation();
            return false;
        }

        var matcher = java.util.regex.Pattern.compile(PASSWORD_PATTERN).matcher(password);
        if (!matcher.matches()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password must be at least 8 characters long, " +
                            "contain at least one lowercase letter, one uppercase letter, one number and one special character from {@,$,!,%,*,?,&} ")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
