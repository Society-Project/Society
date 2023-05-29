package com.society.server.model.entity;

import com.society.server.model.enums.ReactionEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "reactions")
public class ReactionEntity extends BaseEntity {
    private ReactionEnum reactionType;
    private Long respondingUserId;
    private Long targetEntityId;

    @Override
    public int hashCode() {
        return Objects.hash(respondingUserId, targetEntityId);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        ReactionEntity that =(ReactionEntity) o;
        return Objects.equals(respondingUserId, that.respondingUserId)
                && Objects.equals(targetEntityId, that.getTargetEntityId());
    }
}


