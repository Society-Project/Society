package com.society.server.dto.post;

import jakarta.validation.constraints.NotEmpty;

public class CreatePostDTO {

    @NotEmpty
    private String textContent;

    private String imageUrl;

    public CreatePostDTO() {
    }

    public CreatePostDTO(String textContent, String imageUrl) {
        this.textContent = textContent;
        this.imageUrl = imageUrl;
    }

    public String getTextContent() {
        return textContent;
    }

    public CreatePostDTO setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public CreatePostDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
