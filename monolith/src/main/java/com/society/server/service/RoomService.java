package com.society.server.service;

import com.society.server.model.entity.RoomEntity;
import com.society.server.repository.RoomRepository;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public RoomEntity getRoomById(Long roomId) {
        return roomRepository.findById(roomId).orElseThrow();
    }

    public void save(RoomEntity room) {
        this.roomRepository.save(room);
    }


}
