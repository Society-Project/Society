package com.society.server.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "photos")
public class PhotoEntity extends BaseEntity{

    @NotEmpty
    private String imageURL;

    @CreationTimestamp
    private LocalDateTime uploadedOn;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "photos_comments",
           joinColumns = @JoinColumn(name = "photo_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private List<CommentEntity> comments = new ArrayList<>();
    @NotEmpty
    private String photoOwner;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "photo_reactions",
            joinColumns = @JoinColumn(name = "photo_id"),
            inverseJoinColumns = @JoinColumn(name = "reaction_id"))
    private List<ReactionEntity> reactions = new ArrayList<>();
}
