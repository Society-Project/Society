package com.society.server.model.entity;

import com.society.server.model.enums.RelationshipStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "friend_requests")
public class FriendRequestEntity extends BaseEntity {
    @NonNull
    @Enumerated(EnumType.STRING)
    private RelationshipStatus status;

    @ManyToOne
    private UserEntity creator;

    @ManyToOne
    private UserEntity receiver;
}
