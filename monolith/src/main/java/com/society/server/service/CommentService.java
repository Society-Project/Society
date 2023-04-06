package com.society.server.service;

import com.society.server.dto.comment.CommentDTO;
import com.society.server.dto.comment.CreateCommentDTO;
import com.society.server.dto.comment.UpdateCommentDTO;
import com.society.server.exception.NotAuthorizedException;
import com.society.server.exception.ResourceNotFoundException;
import com.society.server.model.entity.CommentEntity;
import com.society.server.model.entity.PostEntity;
import com.society.server.model.entity.UserEntity;
import com.society.server.repository.CommentRepository;
import com.society.server.repository.PostRepository;
import com.society.server.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public void deleteComment(String username, Long postId, Long id) {
        String postAuthorName = postRepository.findById(postId).map(PostEntity::getAuthorUsername).orElseThrow(ResourceNotFoundException::new);
        UserEntity currentUser = userRepository.findByUsername(username).orElseThrow(ResourceNotFoundException::new);

        if (postAuthorName.equals(username) || postService.isAdmin(currentUser)) {
            CommentEntity commentEntity =
                    commentRepository.findById(id).orElseThrow(() ->
                            new ResourceNotFoundException(HttpStatus.NOT_FOUND, "The comment you want to delete does not exist!"));
            PostEntity postEntity = postRepository.findPostEntityById(postId).orElseThrow(ResourceNotFoundException::new);
            postEntity.getComments().remove(commentEntity);
            postRepository.save(postEntity);
            commentRepository.delete(commentEntity);
        } else {
            throw new NotAuthorizedException(HttpStatus.UNAUTHORIZED, "Not enough permissions to delete comment.");
        }
    }

    public CommentDTO updateComment(Long postId, String username, Long id, UpdateCommentDTO updateCommentDTO) {
        PostEntity postEntity = postRepository.findPostEntityById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Post not found."));
        CommentEntity commentEntity =
                commentRepository.findById(id).orElseThrow(() ->
                        new ResourceNotFoundException(HttpStatus.NOT_FOUND, "The comment you are trying to update does not exist!"));

        boolean commentPresent = postEntity.getComments().stream().anyMatch(c -> Objects.equals(c.getId(), id));

        if (!commentEntity.getCreatorUsername().equals(username)) {
            throw new NotAuthorizedException(HttpStatus.UNAUTHORIZED, "Not enough permissions to update comment.");
        } else if (commentPresent) {
            commentEntity.setCommentText(updateCommentDTO.getCommentText())
                    .setImageUrl(updateCommentDTO.getImageUrl())
                    .setUpdatedOn(updateCommentDTO.getUpdatedOn());
            commentRepository.save(commentEntity);
        }
        return modelMapper.map(commentEntity, CommentDTO.class);
    }

    public CommentDTO getComment(Long id) {
        CommentEntity commentEntity = commentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Comment not found."));

        return modelMapper.map(commentEntity, CommentDTO.class);
    }

    public List<CommentDTO> getCommentsByPostId(Long postId) {
        PostEntity postEntity = postRepository.findPostEntityById(postId).orElseThrow(() ->
                new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Post not found"));
        return postEntity
                .getComments()
                .stream()
                .map(commentEntity -> modelMapper.map(commentEntity, CommentDTO.class))
                .collect(Collectors.toList());
    }
}
