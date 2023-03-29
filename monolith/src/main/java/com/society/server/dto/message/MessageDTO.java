package com.society.server.dto.message;

import jakarta.validation.constraints.NotEmpty;

public class MessageDTO {
    @NotEmpty
    private String senderName;
    @NotEmpty
    private String message;

    public MessageDTO(String senderName, String message) {
        this.senderName = senderName;
        this.message = message;
    }

    public MessageDTO() {
    }

    public String getSenderName() {
        return senderName;
    }

    public MessageDTO setSenderName(String senderName) {
        this.senderName = senderName;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public MessageDTO setMessage(String message) {
        this.message = message;
        return this;
    }

}
