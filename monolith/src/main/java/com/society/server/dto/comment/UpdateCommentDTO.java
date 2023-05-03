package com.society.server.dto.comment;

import com.society.server.utils.validators.CommentValidator;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@CommentValidator
public class UpdateCommentDTO {

    private Long id;
    @NotNull
    private String commentText;
    @NotNull
    private String imageUrl;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Long postId;
    private Long photoId;

}
