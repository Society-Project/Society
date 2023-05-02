package com.society.server.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntity {

    @NotNull
    @Column(name = "comment_text")
    private String commentText;

    @NotNull
    private String imageUrl;

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
}
