package com.beval.server.utils.validators.impl;

import com.beval.server.utils.validators.UserUsernameValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.beval.server.config.AppConstants.MAXIMUM_USERNAME_LENGTH;
import static com.beval.server.config.AppConstants.MINIMUM_USERNAME_LENGTH;


public class UserUsernameValidatorImpl implements ConstraintValidator<UserUsernameValidator, String> {
    private String regex = "^(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";

    @Override
    public void initialize(UserUsernameValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.length() >= MINIMUM_USERNAME_LENGTH && value.length() <= MAXIMUM_USERNAME_LENGTH
                && value.matches(regex);
    }
}
