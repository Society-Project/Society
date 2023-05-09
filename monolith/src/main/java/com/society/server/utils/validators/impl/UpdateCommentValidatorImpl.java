package com.society.server.utils.validators.impl;

import com.society.server.dto.comment.UpdateCommentDTO;
import com.society.server.utils.validators.CommentValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UpdateCommentValidatorImpl implements ConstraintValidator<CommentValidator, UpdateCommentDTO> {

    @Override
    public void initialize(CommentValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UpdateCommentDTO dto, ConstraintValidatorContext context) {
        boolean commentTextProvided = dto.getCommentText() != null && !dto.getCommentText().isEmpty();
        boolean imageUrlProvided = dto.getImageUrl() != null && !dto.getImageUrl().isEmpty();
        return commentTextProvided || imageUrlProvided;
    }
}
