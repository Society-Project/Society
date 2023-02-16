package com.society.server.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;



public class CreateCommentDTO {

    public CreateCommentDTO() {
    }

    @NotEmpty
    private String commentText;

    @NotEmpty
    private String imageUrl;

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
