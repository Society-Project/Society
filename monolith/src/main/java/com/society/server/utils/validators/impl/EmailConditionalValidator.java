package com.society.server.utils.validators.impl;

import com.society.server.dto.user.UserSetPersInfoDTO;
import com.society.server.utils.validators.EmailConditional;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailConditionalValidator implements ConstraintValidator<EmailConditional, UserSetPersInfoDTO> {
    private final String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    @Override
    public void initialize(EmailConditional constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserSetPersInfoDTO dto, ConstraintValidatorContext context) {
        if ((dto.getEmail().isEmpty() || dto.getEmail() == null) && (dto.getNewEmail().isEmpty() || dto.getNewEmail() == null))
            return true;
        if (dto.getEmail() == null && dto.getNewEmail() != null) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Please enter your current email address.")
                    .addConstraintViolation();
            return false;
        } else if (dto.getEmail() != null && dto.getNewEmail() == null) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Please enter your new email address.")
                    .addConstraintViolation();
            return false;
        }
        if (dto.getEmail() != null && dto.getNewEmail() != null && !dto.getNewEmail().matches(regex)) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Please enter valid new email address.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
