package com.society.server.model.mapper;

import com.society.server.dto.friendRequest.FriendRequestDTO;
import com.society.server.model.entity.FriendRequestEntity;
import com.society.server.model.entity.user.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-11T21:59:10+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class FriendRequestMapperImpl implements FriendRequestMapper {

    @Override
    public FriendRequestDTO friendRequestEntityToFriendRequestDTO(FriendRequestEntity friendRequestEntity) {
        if ( friendRequestEntity == null ) {
            return null;
        }

        FriendRequestDTO friendRequestDTO = new FriendRequestDTO();

        friendRequestDTO.setCreatorId( friendRequestEntityCreatorId( friendRequestEntity ) );
        friendRequestDTO.setReceiverId( friendRequestEntityReceiverId( friendRequestEntity ) );
        friendRequestDTO.setId( friendRequestEntity.getId() );
        friendRequestDTO.setStatus( friendRequestEntity.getStatus() );

        return friendRequestDTO;
    }

    private Long friendRequestEntityCreatorId(FriendRequestEntity friendRequestEntity) {
        if ( friendRequestEntity == null ) {
            return null;
        }
        UserEntity creator = friendRequestEntity.getCreator();
        if ( creator == null ) {
            return null;
        }
        Long id = creator.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long friendRequestEntityReceiverId(FriendRequestEntity friendRequestEntity) {
        if ( friendRequestEntity == null ) {
            return null;
        }
        UserEntity receiver = friendRequestEntity.getReceiver();
        if ( receiver == null ) {
            return null;
        }
        Long id = receiver.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
