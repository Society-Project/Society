package com.society.server.dto.message;

import com.society.server.model.enums.MessageType;
import jakarta.validation.constraints.NotEmpty;

public class MessageDTO {

    @NotEmpty
    private String senderName;

    @NotEmpty
    private String message;

    @NotEmpty
    private MessageType status;

    public MessageDTO(String senderName, String message, MessageType status) {
        this.senderName = senderName;
        this.message = message;
        this.status = status;
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

    public MessageType getStatus() {
        return status;
    }

    public MessageDTO setStatus(MessageType status) {
        this.status = status;
        return this;
    }
}
