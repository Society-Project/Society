package com.society.server.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
public class PostEntity extends BaseEntity {

    public PostEntity(){
    }

    @NotEmpty
    private String authorUsername;
    @NotEmpty
    private String textContent;

    private String imageUrl;

    @CreationTimestamp
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "posts_comments",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private List<CommentEntity> comments = new ArrayList<>();

    public List<CommentEntity> getComments() {
        return comments;
    }

    public PostEntity setComments(List<CommentEntity> comments) {
        this.comments = comments;
        return this;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public PostEntity setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
        return this;
    }

    public String getTextContent() {
        return textContent;
    }

    public PostEntity setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public PostEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    @Override
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    @Override
    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    @Override
    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }
}
