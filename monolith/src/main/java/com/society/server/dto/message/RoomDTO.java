package com.society.server.dto.message;

import com.society.server.model.enums.RoomEnum;
import com.society.server.utils.validators.IdUnique;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RoomDTO {

    @NotNull
    @IdUnique
    private Long id;
    @NotEmpty
    private String name;

    @NotNull
    private RoomEnum roomEnum;
    //    @Min(1)
    private List<Long> participantsIds;

    private List<MessageDTO> messages;

    public RoomDTO(Long id, String name, RoomEnum roomEnum, List<Long> participantsIds, List<MessageDTO> messages) {
        this.id = id;
        this.name = name;
        this.roomEnum = roomEnum;
        this.participantsIds = participantsIds;
        this.messages = messages;
    }

    public RoomDTO() {
        this.participantsIds = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public RoomDTO setName(String name) {
        this.name = name;
        return this;
    }

    public RoomEnum getRoomEnum() {
        return roomEnum;
    }

    public RoomDTO setRoomEnum(RoomEnum roomEnum) {
        this.roomEnum = roomEnum;
        return this;
    }

    public List<Long> getParticipantsIds() {
        return participantsIds;
    }

    public RoomDTO setParticipantsIds(List<Long> participantsIds) {
        this.participantsIds = participantsIds;
        return this;
    }

    public Long getId() {
        return id;
    }

    public RoomDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public RoomDTO setMessages(List<MessageDTO> messages) {
        this.messages = messages;
        return this;
    }
}
