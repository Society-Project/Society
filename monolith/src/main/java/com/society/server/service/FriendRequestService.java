package com.society.server.service;

import com.society.server.dto.friendRequest.FriendRequestDTO;
import com.society.server.exception.FriendRequestsNotFoundException;
import com.society.server.exception.NotAuthorizedException;
import com.society.server.exception.RelationshipAlreadyExistsException;
import com.society.server.exception.UserNotFoundException;
import com.society.server.model.entity.FriendRequestEntity;
import com.society.server.model.entity.user.UserEntity;
import com.society.server.model.enums.RelationshipStatus;
import com.society.server.model.mapper.FriendRequestMapper;
import com.society.server.repository.FriendRequestRepository;
import com.society.server.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

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
                        () -> new UserNotFoundException(format("User with username: %s cannot be found!", username))
                );

        UserEntity requestReceiver = userRepository.findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException(format("User with id: %d cannot be found!", id))
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
                        new UserNotFoundException(format("User with username: %s cannot be found!", username))
                );
        Optional<List<FriendRequestEntity>> allByReceiver =
                friendRequestRepository.findAllByReceiverAndStatus(user, RelationshipStatus.PENDING);

        if (allByReceiver.isEmpty() || allByReceiver.get().size() == 0) {
            throw new FriendRequestsNotFoundException();
        }
        return allByReceiver.get()
                .stream()
                .map(friendRequestMapper::friendRequestEntityToFriendRequestDTO)
                .collect(Collectors.toList());
    }

    public FriendRequestDTO acceptFriendRequest(Long requestId, String username) throws IllegalAccessException {
        FriendRequestEntity friendRequestEntity = friendRequestRepository.findById(requestId)
                .orElseThrow(FriendRequestsNotFoundException::new);

        if (!friendRequestEntity.getReceiver().getUsername().equals(username)) {
            throw new IllegalAccessException(format("User with username: %s is not receiver of that request," +
                                                    " and don't have access to accept the request", username));
        }
        friendRequestEntity.setStatus(RelationshipStatus.FRIENDS);
        friendRequestEntity.setUpdatedOn(LocalDateTime.now());

        friendRequestRepository.save(friendRequestEntity);

        return friendRequestMapper.friendRequestEntityToFriendRequestDTO(friendRequestEntity);
    }

    public List<FriendRequestDTO> getAllFriends(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException(format("User with username: %s cannot be found!", username))
                );

        Optional<List<FriendRequestEntity>> allFriends =
                friendRequestRepository.findAllFriends(user.getId());

        if (allFriends.isEmpty() || allFriends.get().size() == 0) {
            throw new FriendRequestsNotFoundException();
        }
        return allFriends.get()
                .stream()
                .map(friendRequestMapper::friendRequestEntityToFriendRequestDTO)
                .collect(Collectors.toList());
    }

    public FriendRequestDTO getRequestById(Long requestId, String username) {
        FriendRequestEntity friendRequestEntity = friendRequestRepository.findById(requestId)
                .orElseThrow(FriendRequestsNotFoundException::new);

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException(format("User with username: %s cannot be found!", username))
                );

        FriendRequestDTO friendRequestDTO = friendRequestMapper.
                friendRequestEntityToFriendRequestDTO(friendRequestEntity);

        if (!Objects.equals(user.getId(), friendRequestDTO.getReceiverId())){
            throw new NotAuthorizedException();
        }
        return friendRequestDTO;
    }
}