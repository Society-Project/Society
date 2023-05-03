package com.society.server.utils.validators.impl;
import com.society.server.dto.comment.CreateCommentDTO;
import com.society.server.utils.validators.CommentValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CommentValidatorImpl implements ConstraintValidator<CommentValidator, CreateCommentDTO> {

    @Override
    public void initialize(CommentValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(CreateCommentDTO dto, ConstraintValidatorContext context) {
        boolean commentTextProvided = dto.getCommentText() != null && !dto.getCommentText().isEmpty();
        boolean imageUrlProvided = dto.getImageUrl() != null && !dto.getImageUrl().isEmpty();
        return commentTextProvided || imageUrlProvided;
    }
}
