package com.society.server.repository;

import com.society.server.model.entity.ReactionEntity;
import com.society.server.model.enums.ReactionTargetTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<ReactionEntity, Long> {
    ReactionEntity findByTargetEntityIdAndRespondingUserIdAndTargetEntityTypeEnum(Long targetEntityId, Long userId, ReactionTargetTypeEnum entityType);
    List<ReactionEntity> findAllByTargetEntityIdAndTargetEntityTypeEnum(Long targetEntityId, ReactionTargetTypeEnum entityType);
}
