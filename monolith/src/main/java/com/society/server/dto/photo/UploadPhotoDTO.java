package com.society.server.dto.photo;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadPhotoDTO {

    @NotEmpty
    private String imageURL;
    private LocalDateTime uploadedOn;
    private String photoOwner;
}
