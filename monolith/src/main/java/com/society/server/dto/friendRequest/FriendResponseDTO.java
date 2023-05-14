package com.society.server.dto.friendRequest;

import com.society.server.model.enums.RelationshipStatus;
import jakarta.validation.constraints.NotEmpty;

public class FriendResponseDTO {
    @NotEmpty
    private RelationshipStatus status;

    private String name;

    public FriendResponseDTO(RelationshipStatus status, String name) {
        this.status = status;
        this.name = name;
    }

    public FriendResponseDTO() {
    }

    public RelationshipStatus getStatus() {
        return status;
    }

    public FriendResponseDTO setStatus(RelationshipStatus status) {
        this.status = status;
        return this;
    }

    public String getName() {
        return name;
    }

    public FriendResponseDTO setName(String name) {
        this.name = name;
        return this;
    }
}
