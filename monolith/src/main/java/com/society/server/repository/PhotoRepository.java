package com.society.server.repository;

import com.society.server.model.entity.PhotoEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<PhotoEntity,Long> {
    @EntityGraph(attributePaths = "comments")
    List<PhotoEntity> findAllByPhotoOwner(String photoOwner);
    List<PhotoEntity> findAllByPhotoOwnerEquals(String photoOwner);
}
