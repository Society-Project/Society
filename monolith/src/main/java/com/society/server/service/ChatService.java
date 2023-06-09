package com.society.server.service;

import com.society.server.dto.message.MessageDTO;
import com.society.server.dto.message.RoomDTO;
import com.society.server.exception.UserNotFoundException;
import com.society.server.model.entity.MessageEntity;
import com.society.server.model.entity.RoomEntity;
import com.society.server.model.entity.user.UserEntity;
import com.society.server.model.mapper.RoomMapper;
import com.society.server.repository.MessageRepository;
import com.society.server.repository.UserRepository;
import com.society.server.security.UserPrincipal;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
public class ChatService {
    private final RoomService roomService;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final RoomMapper roomMapper;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatService(RoomService roomService,
                       UserRepository userRepository,
                       MessageRepository messageRepository,
                       RoomMapper roomMapper,
                       SimpMessagingTemplate simpMessagingTemplate) {
        this.roomService = roomService;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.roomMapper = roomMapper;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public MessageDTO saveMessage(MessageDTO messageDTO, Long roomId) {
        RoomEntity room = roomService.getRoomById(roomId);

        UserEntity user = userRepository.findByUsername(messageDTO.getSenderName())
                .orElseThrow(() -> new UserNotFoundException(
                        format("User with username %s is not found!", messageDTO.getSenderName())));

        simpMessagingTemplate.convertAndSend(format("/channel/%d", roomId), messageDTO);

        MessageEntity message = MessageEntity.builder()
                .content(messageDTO.getMessage())
                .room(room)
                .sender(user)
                .build();

        MessageEntity save = messageRepository.save(message);

        room.addMessage(save);

        roomService.save(room);

        return new MessageDTO(save.getSender().getUsername(), save.getContent());
    }

    public RoomDTO createRoom(RoomDTO roomDTO, UserPrincipal userPrincipal) {

        UserEntity creator = userRepository.findByUsername(userPrincipal.getUsername())
                .orElseThrow(() -> new UserNotFoundException(
                        format("User with username %s is not found!", userPrincipal.getUsername()))
                );

        Set<UserEntity> users = roomDTO.getParticipantsIds().size() > 0 ?
                roomDTO.getParticipantsIds()
                        .stream()
                        .map(p -> userRepository.findUserById(p)
                                .orElseThrow(() ->
                                        new UserNotFoundException(format("User with id %d is not found!", p)
                                        )))
                        .collect(Collectors.toSet())
                : new HashSet<>();

        users.add(creator);

        RoomEntity room = RoomEntity.builder()
                .name(roomDTO.getName())
                .roomEnum(roomDTO.getRoomEnum())
                .users(users)
                .messages(new HashSet<>())
                .imageUrl(roomDTO.getImageUrl())
                .build();

        RoomDTO savedRoom = roomService.save(room);

        users.forEach(userEntity -> {
                    userEntity.addRoom(roomService.getRoomById(room.getId()));
                    savedRoom.getParticipantsIds().add(userEntity.getId());
                }
        );

        userRepository.saveAll(users);

        return savedRoom;
    }

    public List<RoomDTO> getRoomsByUser(UserPrincipal userPrincipal) {
        UserEntity user = userRepository.findByUsername(userPrincipal.getUsername())
                .orElseThrow(() -> new UserNotFoundException(
                        format("User with username %s is not found!", userPrincipal.getUsername())));

        List<RoomEntity> roomEntitiesByUser = user.getRooms()
                .stream()
                .map(roomEntity -> roomService.getRoomById(roomEntity.getId()))
                .toList();

        return roomEntitiesByUser
                .stream()
                .map(roomMapper::roomEntityToRoomDTO)
                .toList();
    }

    public RoomDTO getRoomById(Long roomId) {
        RoomEntity room = roomService.getRoomById(roomId);

        return roomMapper.roomEntityToRoomDTO(room);
    }
}
