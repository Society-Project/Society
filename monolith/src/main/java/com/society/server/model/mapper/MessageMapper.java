package com.society.server.model.mapper;

import com.society.server.dto.message.MessageDTO;
import com.society.server.model.entity.MessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mappings({
            @Mapping(source = "sender.username", target = "senderName"),
            @Mapping(source = "content", target = "message")
    })
    MessageDTO messageEntityToMessageDTO(MessageEntity messageEntity);

}
