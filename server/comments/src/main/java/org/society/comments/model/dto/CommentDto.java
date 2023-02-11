package org.society.comments.model.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class CommentDto {
    private UUID id;
    private String commentText;
    private String imageUrl;
    private String creatorUsername;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    public CommentDto() {
    }

    public UUID getId() {
        return id;
    }

    public CommentDto setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getCommentText() {
        return commentText;
    }

    public CommentDto setCommentText(String commentText) {
        this.commentText = commentText;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public CommentDto setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public CommentDto setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public CommentDto setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public CommentDto setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }
}
