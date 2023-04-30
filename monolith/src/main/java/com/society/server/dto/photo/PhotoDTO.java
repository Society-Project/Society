package com.society.server.dto.photo;

import com.society.server.model.entity.CommentEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoDTO {

    private String imageURL;
    private LocalDateTime uploadedOn;
    private List<CommentEntity> comments;
    private String photoOwner;
}
