package com.society.server.utils.validators;

import com.society.server.utils.validators.impl.UniqueIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueIdValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IdUnique {
    String message() default "There is already object with that id!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

