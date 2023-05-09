package com.society.server.model.mapper;

import com.society.server.dto.friendRequest.FriendRequestDTO;
import com.society.server.model.entity.FriendRequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface FriendRequestMapper {

    @Mappings({
            @Mapping(source = "creator.id", target = "creatorId"),
            @Mapping(source = "receiver.id", target = "receiverId")
    })
    FriendRequestDTO friendRequestEntityToFriendRequestDTO(FriendRequestEntity friendRequestEntity);
}
