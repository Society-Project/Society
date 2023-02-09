package com.beval.server.model.entity;

import com.beval.server.model.enums.RoleEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private RoleEnum roleName;
}
