package org.society.comments.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public class CreateCommentDTO {

    @NotEmpty
    private String commentText;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String imageUrl;

    private LocalDateTime createdOn;


    public CreateCommentDTO() {
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public CreateCommentDTO setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public String getCommentText() {
        return commentText;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public CreateCommentDTO setCommentText(String commentText) {
        this.commentText = commentText;
        return this;
    }

    public CreateCommentDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
