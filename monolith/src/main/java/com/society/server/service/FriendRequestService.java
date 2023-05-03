package com.society.server.service;

import com.society.server.dto.friendRequest.FriendRequestDTO;
import com.society.server.exception.FriendRequestsNotFoundException;
import com.society.server.exception.RelationshipAlreadyExistsException;
import com.society.server.exception.ResourceNotFoundException;
import com.society.server.exception.UserNotFoundException;
import com.society.server.model.entity.FriendRequestEntity;
import com.society.server.model.entity.user.UserEntity;
import com.society.server.model.enums.RelationshipStatus;
import com.society.server.model.mapper.FriendRequestMapper;
import com.society.server.repository.FriendRequestRepository;
import com.society.server.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

        if (Objects.equals(requestCreator.getId(), requestReceiver.getId())) {
            throw new IllegalArgumentException("User cannot send friend request to himself");
        }

        Optional<FriendRequestEntity> relationshipExists =
                friendRequestRepository.findIfRelationshipExists(requestCreator.getId(), requestReceiver.getId());

        if (relationshipExists.isPresent()) {
            throw new RelationshipAlreadyExistsException(
                    HttpStatus.BAD_REQUEST, "Relationship between users already exists!");
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

    public List<FriendRequestDTO> getAllReceivedRequests(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("User with username: " + username + " cannot be found!")
                );
        Optional<List<FriendRequestEntity>> allByReceiver = friendRequestRepository.findAllByReceiver(user);
        if (allByReceiver.isEmpty() || allByReceiver.get().size() == 0) {
            throw new FriendRequestsNotFoundException();
        }
        return allByReceiver.get()
                .stream()
                .map(friendRequestMapper::friendRequestEntityToFriendRequestDTO)
                .collect(Collectors.toList());
    }
}
