package com.society.server.repository;

import com.society.server.model.entity.ReactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<ReactionEntity, Long> {
    List<ReactionEntity> findAllByTargetEntityId(Long id);
    ReactionEntity findByRespondingUserIdAndTargetEntityId(Long userId, Long targetEntityId);
}
