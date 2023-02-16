package com.society.server.dto.comment;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public class UpdateCommentDTO {

    private Long id;
    private String commentText;
    private String imageUrl;
    private LocalDateTime updatedOn;

    public UpdateCommentDTO() {
    }

    public Long getId() {
        return id;
    }

    @NotEmpty
    public String getCommentText() {
        return commentText;
    }

    @NotEmpty
    public String getImageUrl() {
        return imageUrl;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }
}
