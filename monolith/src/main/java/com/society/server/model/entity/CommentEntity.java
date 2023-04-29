package com.society.server.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntity {

    public CommentEntity() {
    }

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

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "photo_id")
    private Long photoId;

    public Long getPhotoId() {
        return photoId;
    }

    public CommentEntity setPhotoId(Long photoId) {
        this.photoId = photoId;
        return this;
    }

    public Long getPostId() {
        return postId;
    }

    public CommentEntity setPostId(Long postId) {
        this.postId = postId;
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
