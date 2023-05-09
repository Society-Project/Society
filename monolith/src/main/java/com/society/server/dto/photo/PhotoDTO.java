package com.society.server.dto.photo;

import com.society.server.model.entity.CommentEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoDTO {

    private Long id;
    @NotEmpty
    private String imageURL;
    private LocalDateTime uploadedOn;
    private List<CommentEntity> comments;
    @NotEmpty
    private String photoOwner;
}
