package org.society.comments.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comments")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "comment_text")
    @NotEmpty
    private String commentText;

    private String imageUrl;

    @NotEmpty
    @Column(name = "creator_username")
    private String creatorUsername;

    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    public CommentEntity() {
    }

    public UUID getId() {
        return id;
    }

    public CommentEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getCommentText() {
        return commentText;
    }

    public CommentEntity setCommentText(String commentText) {
        this.commentText = commentText;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public CommentEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public CommentEntity setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public CommentEntity setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public CommentEntity setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }
}
