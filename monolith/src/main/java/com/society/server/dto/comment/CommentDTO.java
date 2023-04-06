package com.society.server.dto.comment;

import java.time.LocalDateTime;

public class CommentDTO {
    private Long id;
    private String commentText;
    private String imageUrl;
    private String creatorUsername;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Long postId;

    public CommentDTO() {
    }

    public Long getPostId() {
        return postId;
    }

    public CommentDTO setPostId(Long postId) {
        this.postId = postId;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;

    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }
}
