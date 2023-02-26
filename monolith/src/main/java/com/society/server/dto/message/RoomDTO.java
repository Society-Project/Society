package com.society.server.dto.message;

import com.society.server.model.enums.RoomEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

public class RoomDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private RoomEnum roomEnum;
    @Min(1)
    private List<Long> participantsIds;

    public RoomDTO(String name, RoomEnum roomEnum, List<Long> participantsIds) {
        this.name = name;
        this.roomEnum = roomEnum;
        this.participantsIds = participantsIds;
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
}
