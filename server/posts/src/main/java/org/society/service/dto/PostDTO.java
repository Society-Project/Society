package org.society.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private UUID id;
    private String creatorUsername;
    private String textContent;
    private String imageUrl;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
