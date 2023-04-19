package com.society.server.model.entity;

import com.society.server.utils.validators.EmailValidator;
import com.society.server.utils.validators.UserUsernameValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @UserUsernameValidator
    private String username;
    @NotNull
    private String password;
    private String firstName;
    private String lastName;
    @EmailValidator
    private String email;
    @Builder.Default
    private boolean enabled = true;
    @Builder.Default
    private boolean locked = false;
    @Builder.Default
    private boolean accountExpired = false;
    @Builder.Default
    private boolean credentialsExpired = false;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_rooms",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    private Set<RoomEntity> rooms;

    public void addRoom(RoomEntity entity) {
        if (this.rooms.size() == 0) {
            this.rooms = new HashSet<>();
        }
        this.rooms.add(entity);
    }
}
