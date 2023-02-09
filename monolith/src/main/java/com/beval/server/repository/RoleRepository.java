package com.beval.server.repository;

import com.beval.server.model.entity.RoleEntity;
import com.beval.server.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRoleName(RoleEnum role);
}
