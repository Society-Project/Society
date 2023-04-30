package com.society.server.model.mapper;

import com.society.server.dto.message.RoomDTO;
import com.society.server.model.entity.BaseEntity;
import com.society.server.model.entity.RoomEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class RoomMapper {

    public RoomDTO roomEntityToRoomDTO(RoomEntity roomEntity) {
        MessageMapper messageMapper = Mappers.getMapper(MessageMapper.class);
        return new RoomDTO(roomEntity.getId(),
                roomEntity.getName(),
                roomEntity.getRoomEnum(),
                roomEntity.getUsers()
                        .stream()
                        .map(BaseEntity::getId)
                        .collect(Collectors.toList()),
                roomEntity.getMessages()
                        .stream()
                        .map(messageMapper::messageEntityToMessageDTO)
                        .collect(Collectors.toList()),
                roomEntity.getImageUrl());
    }
}
