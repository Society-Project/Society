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
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
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

    public void createRoom(RoomDTO roomDTO) {

        Set<UserEntity> users = roomDTO.getParticipantsIds()
                .stream()
                .map(p -> userRepository.findById(p)
                        .orElseThrow(() ->
                                new UserNotFoundException(format("User with id %d is not found!", p)
                                )))
                .collect(Collectors.toSet());

        RoomEntity room = RoomEntity.builder()
                .roomEnum(roomDTO.getRoomEnum())
                .name(roomDTO.getName())
                .messages(new ArrayList<>())
                .users(users)
                .build();

        users.forEach(userEntity -> userEntity.addRoom(room));

        roomService.save(room);
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
    }
}
