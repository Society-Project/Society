package com.society.server.utils.validators;

import com.society.server.utils.validators.impl.EmailConditionalValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Constraint(validatedBy = EmailConditionalValidator.class)
@Documented
public @interface EmailConditional {

    String message() default "Invalid email address";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String conditionalField() default "";
}
