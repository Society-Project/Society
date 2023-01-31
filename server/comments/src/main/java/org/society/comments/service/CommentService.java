package org.society.comments.service;

import org.society.comments.model.dto.CommentDto;
import org.society.comments.model.dto.CreateCommentDTO;

import java.util.UUID;

public interface CommentService {
     CommentDto createComment(String userName, CreateCommentDTO commentDTO);
     void deletePost(UUID commentId);
}
