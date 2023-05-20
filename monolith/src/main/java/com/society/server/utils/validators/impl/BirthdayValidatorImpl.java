package com.society.server.utils.validators.impl;

import com.society.server.dto.payload.SignupDTO;
import com.society.server.utils.validators.BirthdayValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class BirthdayValidatorImpl implements ConstraintValidator<BirthdayValidator, SignupDTO> {

    @Override
    public void initialize(BirthdayValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(SignupDTO dto, ConstraintValidatorContext context) {
        if (dto.getBirthday() == null) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Birthday field is required.")
                    .addConstraintViolation();
            return false;
        } else if (LocalDateTime.now().getYear() - dto.getBirthday().getYear() < 14) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("You must be above 16 years.")
                    .addConstraintViolation();
            return false;
        } /*else if (dto.getBirthday().getMonthValue() > 12) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Invalid month.")
                    .addConstraintViolation();
            return false;
        } else if (dto.getBirthday().getDayOfMonth() > 31) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Invalid day.")
                    .addConstraintViolation();
            return false;
        }*/
        return true;
    }
}
