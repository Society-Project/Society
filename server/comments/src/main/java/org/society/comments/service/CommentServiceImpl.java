package org.society.comments.service;

import org.modelmapper.ModelMapper;
import org.society.comments.model.dto.CommentDto;
import org.society.comments.model.dto.CreateCommentDTO;
import org.society.comments.model.entity.CommentEntity;
import org.society.comments.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(String userName, CreateCommentDTO createCommentDTO) {
        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setCreatorUsername(userName)
                .setCommentText(createCommentDTO.getCommentText())
                .setImageUrl(createCommentDTO.getImageUrl())
                .setCreatedOn(createCommentDTO.getCreatedOn());

        commentRepository.save(commentEntity);

        return modelMapper.map(commentEntity, CommentDto.class);
    }

    @Override
    public void deletePost(UUID commentId) {
        commentRepository.deleteById(commentId);
    }


}
