package com.society.server.dto.friendRequest;

import com.society.server.model.enums.RelationshipStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class FriendRequestDTO {
    @NotNull
    private Long id;

    @NotEmpty
    private RelationshipStatus status;

    @NotNull
    private Long creatorId;

    @NotNull
    private Long receiverId;

    public FriendRequestDTO(Long id, RelationshipStatus status, Long creatorId, Long receiverId) {
        this.id = id;
        this.status = status;
        this.creatorId = creatorId;
        this.receiverId = receiverId;
    }

    public FriendRequestDTO() {
    }

    public Long getId() {
        return id;
    }

    public FriendRequestDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public RelationshipStatus getStatus() {
        return status;
    }

    public FriendRequestDTO setStatus(RelationshipStatus status) {
        this.status = status;
        return this;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public FriendRequestDTO setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public FriendRequestDTO setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
        return this;
    }
}
