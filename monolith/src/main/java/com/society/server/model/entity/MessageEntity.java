package com.society.server.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "messages")
public class MessageEntity extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UserEntity sender;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private RoomEntity room;

    @NotEmpty
    private String content;

}
