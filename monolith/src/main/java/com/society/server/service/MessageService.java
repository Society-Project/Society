package com.society.server.service;

import com.society.server.dto.message.MessageDTO;
import com.society.server.dto.message.RoomDTO;
import com.society.server.exception.UserNotFoundException;
import com.society.server.model.entity.BaseEntity;
import com.society.server.model.entity.MessageEntity;
import com.society.server.model.entity.RoomEntity;
import com.society.server.model.entity.UserEntity;
import com.society.server.repository.MessageRepository;
import com.society.server.repository.UserRepository;
import com.society.server.security.UserPrincipal;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
public class MessageService {

    private final RoomService roomService;

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    public MessageService(RoomService roomService, UserRepository userRepository, MessageRepository messageRepository) {
        this.roomService = roomService;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    public void saveMessage(MessageDTO messageDTO, Long roomId) {
        RoomEntity room = roomService.getRoomById(roomId);

        UserEntity user = userRepository.findByUsername(messageDTO.getSenderName())
                .orElseThrow(() -> new UserNotFoundException(
                        format("User with username %s is not found!", messageDTO.getSenderName())));

        MessageEntity message = MessageEntity.builder()
                .content(messageDTO.getMessage())
                .room(room)
                .sender(user)
                .build();

        room.addMessage(message);

        messageRepository.save(message);
        roomService.save(room);
    }

    public void createRoom(RoomDTO roomDTO, UserPrincipal userPrincipal) {

        UserEntity creator = userRepository.findByUsername(userPrincipal.getUsername())
                .orElseThrow(() -> new UserNotFoundException(
                        format("User with username %s is not found!", userPrincipal.getUsername()))
                );

        Set<UserEntity> users = new HashSet<>();
        if (roomDTO.getParticipantsIds().size() > 0) {
            users = roomDTO.getParticipantsIds()
                    .stream()
                    .map(p -> userRepository.findById(p)
                            .orElseThrow(() ->
                                    new UserNotFoundException(format("User with id %d is not found!", p)
                                    )))
                    .collect(Collectors.toSet());
        }
        users.add(creator);

        RoomEntity room = new RoomEntity(
                roomDTO.getName(),
                roomDTO.getRoomEnum(),
                users,
                new ArrayList<>()
        );
        roomService.save(room);

        users.forEach(userEntity ->
                userEntity.addRoom(
                        roomService.getRoomById(room.getId())
                ));

        userRepository.saveAll(users);
    }

    public List<RoomDTO> getRoomsByUser(UserPrincipal userPrincipal) {
        UserEntity user = userRepository.findByUsername(userPrincipal.getUsername())
                .orElseThrow(() -> new UserNotFoundException(
                        format("User with username %s is not found!", userPrincipal.getUsername())));


        return user.getRooms()
                .stream()
                .map(r ->
                        new RoomDTO(r.getName(),
                                r.getRoomEnum(),
                                r.getUsers()
                                        .stream()
                                        .map(BaseEntity::getId)
                                        .collect(Collectors.toList()))
                )
                .toList();

    }

    public RoomDTO getRoomById(Long roomId) {
        RoomEntity room = roomService.getRoomById(roomId);
        return new RoomDTO(room.getName(),
                room.getRoomEnum(),
                room.getUsers()
                        .stream()
                        .map(BaseEntity::getId)
                        .collect(Collectors.toList()));

        // TODO to get this method work properly,
        //  we should do fetch type eager for users in our room entity but this may cause problem
    }
}
