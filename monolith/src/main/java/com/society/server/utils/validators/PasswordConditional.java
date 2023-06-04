package com.society.server.utils.validators;
import com.society.server.utils.validators.impl.PasswordConditionalValidatorImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Constraint(validatedBy = PasswordConditionalValidatorImpl.class)
@Documented
public @interface PasswordConditional {
    String message() default "Invalid password";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String conditionalField() default "";
}
