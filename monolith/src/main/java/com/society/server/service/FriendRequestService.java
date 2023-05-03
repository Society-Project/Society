package com.society.server.service;

import com.society.server.dto.friendRequest.FriendRequestDTO;
import com.society.server.exception.UserNotFoundException;
import com.society.server.model.entity.FriendRequestEntity;
import com.society.server.model.entity.user.UserEntity;
import com.society.server.model.enums.RelationshipStatus;
import com.society.server.model.mapper.FriendRequestMapper;
import com.society.server.repository.FriendRequestRepository;
import com.society.server.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FriendRequestService {
    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;
    private final FriendRequestMapper friendRequestMapper;

    public FriendRequestService(FriendRequestRepository friendRequestRepository,
                                UserRepository userRepository,
                                FriendRequestMapper friendRequestMapper) {
        this.friendRequestRepository = friendRequestRepository;
        this.userRepository = userRepository;
        this.friendRequestMapper = friendRequestMapper;
    }

    public FriendRequestDTO sendFriendRequest(String username, Long id) {
        UserEntity requestCreator = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UserNotFoundException("User with username: " + username + " cannot be found!")
                );

        UserEntity requestReceiver = userRepository.findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException("User with id: " + id + " cannot be found!")
                );

        Optional<FriendRequestEntity> relationshipExists =
                friendRequestRepository.findIfRelationshipExists(requestCreator.getId(), requestReceiver.getId());

        if (relationshipExists.isPresent()) {
            return friendRequestMapper.friendRequestEntityToFriendRequestDTO(relationshipExists.get());
        }
        FriendRequestEntity entity = FriendRequestEntity
                .builder()
                .creator(requestCreator)
                .receiver(requestReceiver)
                .status(RelationshipStatus.PENDING)
                .build();
        FriendRequestEntity save = friendRequestRepository.save(entity);

        return friendRequestMapper.friendRequestEntityToFriendRequestDTO(save);
    }
}
