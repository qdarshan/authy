package org.arsh.auth.core.service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.arsh.auth.core.service.validator.constraint.UsernameOrEmailRequiredValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = UsernameOrEmailRequiredValidator.class)
@Documented
public @interface UsernameOrEmailRequired {
    String message() default "Either username or password must be present";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
