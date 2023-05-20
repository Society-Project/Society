package com.society.server.service;

import com.society.server.dto.friendRequest.FriendRequestDTO;
import com.society.server.dto.friendRequest.FriendResponseDTO;
import com.society.server.exception.*;
import com.society.server.exception.userRelationshipsExceptions.*;
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
            throw new NotAuthorizedException("User cannot send friend request to himself");
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

    public List<FriendResponseDTO> getAllReceivedRequests(String username) {
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
                .map(entity -> friendEntityToFriendResponse(entity, user))
                .collect(Collectors.toList());
    }

    public FriendRequestDTO acceptFriendRequest(Long requestId, String username) {
        FriendRequestEntity friendRequestEntity = friendRequestRepository.findById(requestId)
                .orElseThrow(FriendRequestsNotFoundException::new);

        if (!friendRequestEntity.getReceiver().getUsername().equals(username)) {
            throw new NotAuthorizedException("You are not authorized to accept this request");
        }
        friendRequestEntity.setStatus(RelationshipStatus.FRIENDS);
        friendRequestEntity.setUpdatedOn(LocalDateTime.now());

        friendRequestRepository.save(friendRequestEntity);

        return friendRequestMapper.friendRequestEntityToFriendRequestDTO(friendRequestEntity);
    }

    public List<FriendResponseDTO> getAllFriends(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(format("User with id: %d cannot be found!", userId))
                );

        List<FriendRequestEntity> allFriends =
                friendRequestRepository.findAllFriends(user.getId())
                        .orElseThrow(FriendRequestsNotFoundException::new)
                        .stream()
                        .filter(entity -> entity.getStatus().equals(RelationshipStatus.FRIENDS))
                        .toList();

        if (allFriends.isEmpty()) {
            throw new FriendRequestsNotFoundException();
        }

        return allFriends
                .stream()
                .map(entity -> friendEntityToFriendResponse(entity, user))
                .collect(Collectors.toList());
    }

    public FriendResponseDTO getRequestById(Long requestId, String username) {
        FriendRequestEntity friendRequestEntity = friendRequestRepository.findByIdAndStatus(requestId, RelationshipStatus.PENDING)
                .orElseThrow(FriendRequestsNotFoundException::new);

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException(format("User with username: %s cannot be found!", username))
                );

        if (!Objects.equals(user.getId(), friendRequestEntity.getReceiver().getId())) {
            throw new NotAuthorizedException();
        }
        return friendEntityToFriendResponse(friendRequestEntity, user);
    }

    public void deleteRequestById(Long requestId, String username) {
        FriendRequestDTO requestDTO = friendRequestMapper
                .friendRequestEntityToFriendRequestDTO(friendRequestRepository.findById(requestId)
                        .orElseThrow(FriendRequestsNotFoundException::new));

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException(format("User with username: %s cannot be found!", username))
                );

        if (!Objects.equals(requestDTO.getReceiverId(), user.getId())
            && !Objects.equals(requestDTO.getCreatorId(), user.getId())) {
            throw new NotAuthorizedException("You are not authorized to delete this request!");
        }
        friendRequestRepository.deleteById(requestId);
    }

    public void blockUser(Long userIdToBlock, String principalUsername) {
        UserEntity user = userRepository.findByUsername(principalUsername)
                .orElseThrow(() ->
                        new UserNotFoundException(format("User with username: %s cannot be found!", principalUsername))
                );

        UserEntity userToBlock = userRepository.findById(userIdToBlock)
                .orElseThrow(() ->
                        new UserNotFoundException(format("User with id: %d cannot be found!", userIdToBlock))
                );

        Optional<FriendRequestEntity> ifRelationshipExists =
                friendRequestRepository.findIfRelationshipExists(user.getId(), userToBlock.getId());

        if (ifRelationshipExists.isEmpty()) {
            throw new RelationshipNotExistException(HttpStatus.BAD_REQUEST, "Relationship between users don't exist");
        }
        FriendRequestEntity entity = ifRelationshipExists.get();
        if (entity.getStatus().equals(RelationshipStatus.BLOCKED)) {
            throw new UserAlreadyBlockedException(HttpStatus.BAD_REQUEST, "Relationship is already blocked");
        }
        entity.setStatus(RelationshipStatus.BLOCKED);
        entity.setUpdatedOn(LocalDateTime.now());

        friendRequestRepository.save(entity);
    }

    public void unblockUser(Long userIdToUnblock, String principalUsername) {
        UserEntity user = userRepository.findByUsername(principalUsername)
                .orElseThrow(() ->
                        new UserNotFoundException(format("User with username: %s cannot be found!", principalUsername))
                );

        UserEntity userToUnblock = userRepository.findById(userIdToUnblock)
                .orElseThrow(() ->
                        new UserNotFoundException(format("User with id: %d cannot be found!", userIdToUnblock))
                );

        Optional<FriendRequestEntity> ifRelationshipExists =
                friendRequestRepository.findIfRelationshipExists(user.getId(), userToUnblock.getId());

        if (ifRelationshipExists.isEmpty()) {
            throw new RelationshipNotExistException(HttpStatus.BAD_REQUEST, "Relationship between users don't exist");
        }
        FriendRequestEntity entity = ifRelationshipExists.get();
        if (!entity.getStatus().equals(RelationshipStatus.BLOCKED)) {
            throw new UserNotBlockedException(HttpStatus.BAD_REQUEST, "Relationship is not blocked");
        }
        friendRequestRepository.delete(entity);
    }

    private FriendResponseDTO friendEntityToFriendResponse(FriendRequestEntity entity, UserEntity currentUser) {
        FriendResponseDTO friendResponseDTO;
        if (currentUser.equals(entity.getReceiver())) {
            friendResponseDTO = new FriendResponseDTO(entity.getStatus(),
                    entity.getCreator().getFirstName() + " " + entity.getCreator().getLastName());
        } else {
            friendResponseDTO = new FriendResponseDTO(entity.getStatus(),
                    entity.getReceiver().getFirstName() + " " + entity.getReceiver().getLastName());
        }
        return friendResponseDTO;
    }


}
