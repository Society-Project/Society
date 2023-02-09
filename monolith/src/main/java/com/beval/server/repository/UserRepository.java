package com.beval.server.repository;

import com.beval.server.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsernameOrEmail(String username, String email);

    boolean existsByUsername(String usernameOrEmail);
    boolean existsByEmail(String usernameOrEmail);
}
