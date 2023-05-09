package com.society.server.utils.validators;

import com.society.server.utils.validators.impl.CommentValidatorImpl;
import com.society.server.utils.validators.impl.UpdateCommentValidatorImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = { CommentValidatorImpl.class, UpdateCommentValidatorImpl.class})
@Target({ ElementType.TYPE ,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CommentValidator {

    String message() default "Either comment text or image URL must be provided";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
