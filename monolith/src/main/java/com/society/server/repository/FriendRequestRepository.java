package com.society.server.repository;

import com.society.server.model.entity.FriendRequestEntity;
import com.society.server.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequestEntity, Long> {

    @Query(value = "SELECT * FROM friend_requests " +
                   "WHERE creator_id = :requestCreator " +
                   "AND receiver_id = :requestReceiver " +
                   "OR receiver_id = :requestCreator " +
                   "AND creator_id = :requestReceiver",
            nativeQuery = true)
    Optional<FriendRequestEntity> findIfRelationshipExists(Long requestCreator, Long requestReceiver);
}
//app:
//  jwtSecret: "6jCTFSacGgVfffoK/c/DQg4z0848R9a71acEiKgjozs="
//  jwtExpirationMs: 600000
//  dataLoader: true
//
//server:
//  port: 8080
//#logging:
//#    level:
//#        root: debug
//spring:
//  datasource:
//    driverClassName: org.postgresql.Driver
//    password: "password"
//    url: "jdbc:postgresql://localhost:5432/society"
//    username: "postgres"
//  jpa:
//    hibernate:
//      ddl-auto: create-drop
//    properties:
//      hibernate:
//        dialect: org.hibernate.dialect.PostgreSQLDialect
//        format_sql: true
//        show_sql: false
//    open-in-view: false
//  servlet:
//    multipart:
//      max-file-size: 10MB
//      max-request-size: 10MB
//spring-doc:
//  packagesToScan: com.society.server.api.v1
//  pathsToMatch: /api/v1/**
//  swagger-ui:
//    operationsSorter: method
//    tagsSorter: alpha
//    url: /v3/api-docs
//  show-actuator: true
//  api-docs:
//    version: openapi_3_0
//#    path: /documentation
//
//
//logging:
//  level:
//    org.hibernate.SQL: DEBUG
//    org.hibernate.type.descriptor.sql.BasicBinder: TRACE