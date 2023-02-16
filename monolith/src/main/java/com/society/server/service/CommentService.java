package com.society.server.service;

import com.society.server.dto.comment.CommentDTO;
import com.society.server.dto.comment.CreateCommentDTO;
import com.society.server.dto.comment.UpdateCommentDTO;
import com.society.server.exception.ResourceNotFoundException;
import com.society.server.model.entity.CommentEntity;
import com.society.server.model.entity.UserEntity;
import com.society.server.repository.CommentRepository;
import com.society.server.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public CommentDTO createComment(String username, CreateCommentDTO createCommentDTO) {
        CommentEntity commentEntity = new CommentEntity();
        //Todo: Create custom exception -> UserNotFound.
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(ResourceNotFoundException::new);


        commentEntity.setCreatorUsername(username)
                .setCommentText(createCommentDTO.getCommentText())
                .setImageUrl(createCommentDTO.getImageUrl())
                        .setAuthor(userEntity);

        commentRepository.save(commentEntity);

        return modelMapper.map(commentEntity, CommentDTO.class);
    }

    public void deleteComment(Long id) {

        CommentEntity commentEntity =
                commentRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        commentRepository.delete(commentEntity);
    }

    public CommentDTO updateComment(Long id, UpdateCommentDTO updateCommentDTO) {
        CommentEntity commentEntity =
                commentRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        commentEntity.setCommentText(updateCommentDTO.getCommentText())
                .setImageUrl(updateCommentDTO.getImageUrl())
                .setUpdatedOn(updateCommentDTO.getUpdatedOn());

        commentRepository.save(commentEntity);

        return modelMapper.map(commentEntity, CommentDTO.class);
    }
}
