package com.society.server.service;

import com.society.server.dto.comment.CommentDTO;
import com.society.server.dto.comment.CreateCommentDTO;
import com.society.server.dto.comment.UpdateCommentDTO;
import com.society.server.dto.post.PostDTO;
import com.society.server.exception.ResourceNotFoundException;
import com.society.server.model.entity.CommentEntity;
import com.society.server.model.entity.PostEntity;
import com.society.server.repository.CommentRepository;
import com.society.server.repository.PostRepository;
import com.society.server.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostService postService, ModelMapper modelMapper, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public CommentDTO createComment(Long id, String username, CreateCommentDTO createCommentDTO) {
        PostEntity postEntity = postRepository.findPostEntityById(id).orElseThrow(ResourceNotFoundException::new);
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCreatorUsername(username)
                .setCommentText(createCommentDTO.getCommentText())
                .setImageUrl(createCommentDTO.getImageUrl())
                    .setPostId(postEntity.getId());
        commentRepository.save(commentEntity);

        postEntity.getComments().add(commentEntity);

        postRepository.save(postEntity);
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
