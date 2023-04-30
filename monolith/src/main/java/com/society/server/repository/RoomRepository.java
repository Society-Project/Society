package com.society.server.repository;

import com.society.server.model.entity.RoomEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

    @EntityGraph(attributePaths = {
            "messages",
            "users"
    })
    Optional<RoomEntity> findRoomEntityById(Long roomId);

}
