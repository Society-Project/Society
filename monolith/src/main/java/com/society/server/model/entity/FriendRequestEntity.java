package com.society.server.model.entity;

import com.society.server.model.enums.RelationshipStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty
    @ManyToOne
    private UserEntity creator;

    @NotEmpty
    @ManyToOne
    private UserEntity receiver;
}
