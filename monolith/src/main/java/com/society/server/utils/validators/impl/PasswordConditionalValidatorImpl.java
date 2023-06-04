package com.society.server.utils.validators.impl;

import com.society.server.dto.user.UserSetPersInfoDTO;
import com.society.server.utils.validators.PasswordConditional;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConditionalValidatorImpl implements ConstraintValidator<PasswordConditional, UserSetPersInfoDTO> {
    @Override
    public void initialize(PasswordConditional constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserSetPersInfoDTO dto, ConstraintValidatorContext context) {
        if (dto.getPassword().isEmpty() && dto.getNewPassword().isEmpty() && dto.getRepeatNewPassword().isEmpty())
            return true;
        if (!dto.getPassword().isEmpty() && dto.getNewPassword().isEmpty() && !dto.getRepeatNewPassword().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Please enter your new password")
                    .addConstraintViolation();
            return false;
        }
        if (!dto.getPassword().isEmpty() && !dto.getNewPassword().isEmpty() && dto.getRepeatNewPassword().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Please repeat your new password")
                    .addConstraintViolation();
            return false;
        }
        if (dto.getPassword().isEmpty() && (!dto.getNewPassword().isEmpty() || !dto.getRepeatNewPassword().isEmpty())) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Please enter your old password")
                    .addConstraintViolation();
            return false;
        }
        if (!dto.getPassword().isEmpty() && dto.getNewPassword().length() < 6 && !dto.getRepeatNewPassword().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Password must be at least 6 symbols")
                    .addConstraintViolation();
            return false;
        }
        if (!dto.getPassword().isEmpty() && !dto.getNewPassword().equals(dto.getRepeatNewPassword())) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Passwords do not match")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
