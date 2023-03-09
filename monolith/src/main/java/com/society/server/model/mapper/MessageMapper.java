package com.society.server.model.mapper;

import com.society.server.dto.message.MessageDTO;
import com.society.server.model.entity.MessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(target = "senderName", source = "sender.username")
    @Mapping(target = "message", source = "content")
    MessageDTO messageEntityToMessageDTO(MessageEntity messageEntity);

}
