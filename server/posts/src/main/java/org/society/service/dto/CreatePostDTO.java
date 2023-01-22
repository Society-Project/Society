package org.society.service.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePostDTO {
    @NotEmpty
    private String textContent;
    @NotEmpty
    private String imageUrl;
}
