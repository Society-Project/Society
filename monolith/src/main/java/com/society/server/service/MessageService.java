package com.society.server.service;

import com.society.server.dto.message.MessageDTO;
import com.society.server.dto.message.RoomDTO;
import com.society.server.model.entity.MessageEntity;
import com.society.server.model.entity.RoomEntity;
import com.society.server.model.entity.UserEntity;
import com.society.server.repository.MessageRepository;
import com.society.server.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

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
                .orElseThrow();


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
                .map(p -> userRepository.findById(p).orElseThrow())
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

}
