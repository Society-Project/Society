package com.society.server.model.mapper;

import com.society.server.dto.message.MessageDTO;
import com.society.server.model.entity.MessageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class MessageMapper {

    public MessageDTO messageEntityToMessageDTO(MessageEntity messageEntity) {
        return new MessageDTO(messageEntity.getSender().getUsername(),
                messageEntity.getContent());
    }

}
