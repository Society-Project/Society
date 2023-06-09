package com.society.server.service;

import com.society.server.dto.message.RoomDTO;
import com.society.server.exception.ResourceNotFoundException;
import com.society.server.model.entity.RoomEntity;
import com.society.server.model.mapper.RoomMapper;
import com.society.server.repository.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public RoomService(RoomRepository roomRepository, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    public RoomEntity getRoomById(Long roomId) {
        return roomRepository.findRoomEntityById(roomId).orElseThrow(() ->
                new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Room with id: " + roomId + " cannot be found")
        );
    }

    public RoomDTO save(RoomEntity room) {
        if (room == null) {
            throw new NullPointerException("RoomEntity value is null!");
        }
        RoomEntity save = this.roomRepository.save(room);
        return roomMapper.roomEntityToRoomDTO(roomRepository.findRoomEntityById(save.getId()).get());
    }


}
