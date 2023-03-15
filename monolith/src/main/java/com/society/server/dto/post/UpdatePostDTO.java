package com.society.server.dto.post;

import jakarta.validation.constraints.NotEmpty;

public class UpdatePostDTO {

    @NotEmpty
    private String textContent;

    private String imageUrl;

    public UpdatePostDTO() {
    }

    public String getTextContent() {
        return textContent;
    }

    public UpdatePostDTO setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UpdatePostDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
