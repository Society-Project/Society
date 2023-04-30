package com.society.server.repository;

import com.society.server.model.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findCommentEntityByPhotoId(Long id);
    List<CommentEntity> findCommentsByPostId(Long id);
}
