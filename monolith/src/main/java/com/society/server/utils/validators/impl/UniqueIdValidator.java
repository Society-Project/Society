package com.society.server.utils.validators.impl;

import com.society.server.repository.RoomRepository;
import com.society.server.utils.validators.IdUnique;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueIdValidator implements ConstraintValidator<IdUnique, Long> {
    private final RoomRepository roomRepository;

    public UniqueIdValidator(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void initialize(IdUnique constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return roomRepository.findById(value).isEmpty();
    }
}
