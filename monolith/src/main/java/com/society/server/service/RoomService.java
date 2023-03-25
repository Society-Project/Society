package com.society.server.service;

import com.society.server.exception.ResourceNotFoundException;
import com.society.server.model.entity.RoomEntity;
import com.society.server.repository.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public RoomEntity getRoomById(Long roomId) {
        return roomRepository.findById(roomId).orElseThrow(() ->
                new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Room with id: " + roomId + " cannot be found")
        );
    }

    public void save(RoomEntity room) {
        if (room == null) {
            throw new NullPointerException("RoomEntity value is null!");
        }
        this.roomRepository.save(room);

    }


}
