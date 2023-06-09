package com.society.server.repository;

import com.society.server.model.entity.user.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsernameOrEmail(String username, String email);

    @EntityGraph(value = "UserEntity.userPersonalInfo", type = EntityGraph.EntityGraphType.FETCH)
    Optional<UserEntity> findById(Long userId);

    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String usernameOrEmail);

    boolean existsByEmail(String usernameOrEmail);

    Optional<UserEntity> findUserById(Long userId);
}
