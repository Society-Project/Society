package com.society.server.repository;

import com.society.server.model.entity.FriendRequestEntity;
import com.society.server.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequestEntity, Long> {

    @Query(value = "SELECT * FROM friend_requests " +
                   "WHERE creator_id = :requestCreator " +
                   "AND receiver_id = :requestReceiver " +
                   "OR receiver_id = :requestCreator " +
                   "AND creator_id = :requestReceiver",
            nativeQuery = true)
    Optional<FriendRequestEntity> findIfRelationshipExists(Long requestCreator, Long requestReceiver);
}
