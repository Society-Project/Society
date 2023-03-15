package com.society.server.dto.post;
import com.society.server.dto.comment.CommentDTO;

import java.time.LocalDateTime;
import java.util.List;

public class PostDTO {
    private Long id;
    private String authorUsername;
    private String textContent;
    private String imageUrl;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private List<CommentDTO> comments;

    public PostDTO() {
    }

    public Long getId() {
        return id;
    }

    public PostDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public PostDTO setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
        return this;
    }

    public String getTextContent() {
        return textContent;
    }

    public PostDTO setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public PostDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public PostDTO setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public PostDTO setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public PostDTO setComments(List<CommentDTO> comments) {
        this.comments = comments;
        return this;
    }
}
