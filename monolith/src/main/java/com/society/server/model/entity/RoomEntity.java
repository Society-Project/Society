package com.society.server.model.entity;

import com.society.server.model.enums.RoomEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rooms")
public class RoomEntity extends BaseEntity {

    @NotEmpty
    private String name;

    @NotEmpty
    @Enumerated(EnumType.STRING)
    private RoomEnum roomEnum;

    @NotEmpty
    @ManyToMany(mappedBy = "rooms", targetEntity = UserEntity.class)
    private Set<UserEntity> users;

    @OneToMany(mappedBy = "room", targetEntity = MessageEntity.class)
    private List<MessageEntity> messages;
}
