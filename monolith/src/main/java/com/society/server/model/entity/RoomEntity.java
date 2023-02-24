package com.society.server.model.entity;

import com.society.server.model.enums.RoomEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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

    @NotNull
    private String name;

    @NotEmpty
    @ManyToMany(mappedBy = "rooms", targetEntity = UserEntity.class,
            fetch = FetchType.EAGER)
    private Set<UserEntity> users;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoomEnum roomEnum;


}
