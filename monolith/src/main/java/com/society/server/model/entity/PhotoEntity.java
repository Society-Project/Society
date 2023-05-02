package com.society.server.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    @OneToMany
    private List<CommentEntity> comments = new ArrayList<>();
    @NotEmpty
    private String photoOwner;
}
