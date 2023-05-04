package com.society.server.dto.comment;

import com.society.server.utils.validators.CommentValidator;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@CommentValidator
public class CreateCommentDTO {

    @NotNull
    private String commentText;
    @NotNull
    private String imageUrl;

}
