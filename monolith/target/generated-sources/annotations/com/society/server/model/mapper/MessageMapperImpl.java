package com.society.server.model.mapper;

import com.society.server.dto.message.MessageDTO;
import com.society.server.model.entity.MessageEntity;
import com.society.server.model.entity.user.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-08T13:06:11+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class MessageMapperImpl implements MessageMapper {

    @Override
    public MessageDTO messageEntityToMessageDTO(MessageEntity messageEntity) {
        if ( messageEntity == null ) {
            return null;
        }

        MessageDTO messageDTO = new MessageDTO();

        messageDTO.setSenderName( messageEntitySenderUsername( messageEntity ) );
        messageDTO.setMessage( messageEntity.getContent() );

        return messageDTO;
    }

    private String messageEntitySenderUsername(MessageEntity messageEntity) {
        if ( messageEntity == null ) {
            return null;
        }
        UserEntity sender = messageEntity.getSender();
        if ( sender == null ) {
            return null;
        }
        String username = sender.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }
}
