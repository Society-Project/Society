package com.society.server.service;

import com.society.server.dto.comment.CommentDTO;
import com.society.server.dto.comment.CreateCommentDTO;
import com.society.server.dto.comment.UpdateCommentDTO;
import com.society.server.dto.reaction.ReactionDTO;
import com.society.server.exception.NotAuthorizedException;
import com.society.server.exception.ResourceNotFoundException;
import com.society.server.model.entity.CommentEntity;
import com.society.server.model.entity.PhotoEntity;
import com.society.server.model.entity.PostEntity;
import com.society.server.model.entity.ReactionEntity;
import com.society.server.model.entity.user.UserEntity;
import com.society.server.model.enums.ReactionTargetTypeEnum;
import com.society.server.repository.*;
import com.society.server.security.IAuthenticationFacade;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PhotoRepository photoRepository;
    private final IAuthenticationFacade authenticationFacade;
    private final ReactionRepository reactionRepository;

    public CommentService(CommentRepository commentRepository, PostService postService,
                          ModelMapper modelMapper, UserRepository userRepository, PostRepository postRepository,
                          PhotoRepository photoRepository, IAuthenticationFacade authenticationFacade, ReactionRepository reactionRepository) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.photoRepository = photoRepository;
        this.authenticationFacade = authenticationFacade;
        this.reactionRepository = reactionRepository;
    }

    public CommentDTO createCommentToPostId(Long postId, CreateCommentDTO createCommentDTO) {
        PostEntity postEntity = postRepository
                .findPostEntityById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Post with id " + postId + " not found."));
        String username = authenticationFacade.getAuthentication().getName();
        if (!userRepository.existsByUsername(username)) {
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "User with username " + username + " not found.");
        }

        CommentEntity commentEntity = CommentEntity.builder()
                .creatorUsername(username)
                .commentText(createCommentDTO.getCommentText())
                .imageUrl(createCommentDTO.getImageUrl())
                .postId(postEntity.getId())
                .reactions(new ArrayList<>())
                .build();
        commentRepository.save(commentEntity);

        postEntity.getComments().add(commentEntity);

        postRepository.save(postEntity);
        return modelMapper.map(commentEntity, CommentDTO.class);
    }

    @Transactional
    public CommentDTO createCommentToPhotoId(Long photoId, CreateCommentDTO createCommentDTO) {
        PhotoEntity photoEntity = photoRepository.findById(photoId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND,
                        "Photo with id " + photoId + " not found."));
        String username = authenticationFacade.getAuthentication().getName();
        if (!userRepository.existsByUsername(username)) throw new ResourceNotFoundException(HttpStatus.NOT_FOUND,
                "User with username " + username + " not found.");

        List<CommentEntity> commentsByPhotoId = commentRepository.findAllByPhotoId(photoId);
        CommentEntity commentEntity = CommentEntity.builder()
                .creatorUsername(username)
                .commentText(createCommentDTO.getCommentText())
                .photoId(photoId)
                .reactions(new ArrayList<>())
                .imageUrl(createCommentDTO.getImageUrl())
                .build();
        commentRepository.save(commentEntity);
        commentsByPhotoId.add(commentEntity);
        photoEntity.setComments(commentsByPhotoId);
        photoRepository.save(photoEntity);
        return modelMapper.map(commentEntity, CommentDTO.class);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        CommentEntity commentEntity =
                commentRepository.findById(commentId).orElseThrow(() ->
                        new ResourceNotFoundException(HttpStatus.NOT_FOUND, "The comment you want to delete does not exist!"));
        String username = authenticationFacade.getAuthentication().getName();

        UserEntity currentUser = userRepository.findByUsername(username).orElseThrow(ResourceNotFoundException::new);
        if (commentEntity.getPhotoId() == null) {
            String postAuthorName = postRepository.findById(commentEntity.getPostId()).map(PostEntity::getAuthorUsername)
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Post with id " + commentEntity.getPostId() + " not found"));
            if (postAuthorName.equals(username) || postService.isAdmin(currentUser) || commentEntity.getCreatorUsername().equals(username)) {
                PostEntity postEntity = postRepository.findPostEntityById(commentEntity.getPostId()).orElseThrow(ResourceNotFoundException::new);
                postEntity.getComments().remove(commentEntity);
                postRepository.save(postEntity);
            } else {
                throw new NotAuthorizedException(HttpStatus.UNAUTHORIZED, "Not enough permissions to delete comment.");
            }
        } else if (commentEntity.getPostId() == null) {
            String photoAuthorName = photoRepository.findById(commentEntity.getPhotoId()).map(PhotoEntity::getPhotoOwner)
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Photo with id " + commentEntity.getPhotoId() + " not found"));
            if (photoAuthorName.equals(username) || postService.isAdmin(currentUser) || commentEntity.getCreatorUsername().equals(username)) {
                PhotoEntity photoEntity = photoRepository.findById(commentEntity.getPhotoId()).orElseThrow(ResourceNotFoundException::new);
                List<CommentEntity> allByPhotoId = commentRepository.findAllByPhotoId(photoEntity.getId());
                allByPhotoId.remove(commentEntity);
                photoEntity.setComments(allByPhotoId);
                photoRepository.save(photoEntity);
            } else {
                throw new NotAuthorizedException(HttpStatus.UNAUTHORIZED, "Not enough permissions to delete comment.");
            }
        }
        commentRepository.delete(commentEntity);
    }

    public CommentDTO updateComment(Long commentId, UpdateCommentDTO updateCommentDTO) {
        CommentEntity commentEntity =
                commentRepository.findById(commentId).orElseThrow(() ->
                        new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Comment with id " + commentId + " not found"));
        String username = authenticationFacade.getAuthentication().getName();
        if (!commentEntity.getCreatorUsername().equals(username)) {
            throw new NotAuthorizedException(HttpStatus.UNAUTHORIZED, "Not enough permissions to update comment.");
        } else {
            List<ReactionEntity> commentReactions = reactionRepository
                    .findAllByTargetEntityIdAndTargetEntityTypeEnum(commentEntity.getId(), ReactionTargetTypeEnum.COMMENT);
            commentEntity.setReactions(commentReactions);
            commentEntity.setCommentText(updateCommentDTO.getCommentText());
            commentEntity.setImageUrl(updateCommentDTO.getImageUrl());
            commentEntity.setUpdatedOn(LocalDateTime.now());
            commentRepository.save(commentEntity);
        }
        return modelMapper.map(commentEntity, CommentDTO.class);
    }

    public CommentDTO getComment(Long id) {
        CommentEntity commentEntity = commentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Comment with id " + id + " not exist!"));
        List<ReactionEntity> commentReactions = reactionRepository
                .findAllByTargetEntityIdAndTargetEntityTypeEnum(id, ReactionTargetTypeEnum.COMMENT);
        commentEntity.setReactions(commentReactions);
        return modelMapper.map(commentEntity, CommentDTO.class);
    }

    public List<CommentDTO> getCommentsByPostId(Long postId) {
        PostEntity postEntity = postRepository.findPostEntityById(postId).orElseThrow(() ->
                new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Post with id " + postId + " not exist!"));

        return postEntity
                .getComments()
                .stream()
                .map(commentEntity -> {
                    List<ReactionEntity> commentReactions = reactionRepository
                            .findAllByTargetEntityIdAndTargetEntityTypeEnum(commentEntity.getId(), ReactionTargetTypeEnum.COMMENT);
                    commentEntity.setReactions(commentReactions);
                    return modelMapper.map(commentEntity, CommentDTO.class);
                })
                .collect(Collectors.toList());
    }

    public List<CommentDTO> getCommentsByPhotoId(Long photoId) {
        if (photoRepository.findById(photoId).isEmpty())
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Photo with id " + photoId + " not exist!");
        List<CommentEntity> commentsByPhotoId = commentRepository.findAllByPhotoId(photoId);
        return commentsByPhotoId
                .stream()
                .map(commentEntity -> {
                    List<ReactionEntity> commentReactions = reactionRepository
                            .findAllByTargetEntityIdAndTargetEntityTypeEnum(commentEntity.getId(), ReactionTargetTypeEnum.COMMENT);
                    commentEntity.setReactions(commentReactions);
                    return modelMapper.map(commentEntity, CommentDTO.class);
                })
                .collect(Collectors.toList());
    }


    public void reactToComment(Long commentId, ReactionDTO reactionDTO) {
        UserEntity userEntity = userRepository.findByUsername(authenticationFacade.getAuthentication().getName())
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "User not found"));
        ReactionEntity reactionEntity = reactionRepository
                .findByTargetEntityIdAndRespondingUserIdAndTargetEntityTypeEnum(commentId, userEntity.getId(), ReactionTargetTypeEnum.COMMENT);
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Comment with id " + commentId + " not found"));

        if (reactionEntity != null) {
            if (reactionDTO.getReactionType() == null) {
                List<ReactionEntity> commentReactions = reactionRepository
                        .findAllByTargetEntityIdAndTargetEntityTypeEnum(commentId, ReactionTargetTypeEnum.COMMENT);
                commentReactions.remove(reactionEntity);
                commentEntity.setReactions(commentReactions);
                commentRepository.save(commentEntity);
                reactionRepository.delete(reactionEntity);
            } else {
                reactionEntity.setReactionType(reactionDTO.getReactionType());
                reactionRepository.save(reactionEntity);
            }
        } else {
            if (reactionDTO.getReactionType() != null) {
                List<ReactionEntity> commentReactions = reactionRepository
                        .findAllByTargetEntityIdAndTargetEntityTypeEnum(commentId, ReactionTargetTypeEnum.COMMENT);
                ReactionEntity newReaction =
                        new ReactionEntity(reactionDTO.getReactionType(), userEntity.getId(), commentId, ReactionTargetTypeEnum.COMMENT);
                reactionRepository.save(newReaction);
                commentReactions.add(newReaction);
                commentEntity.setReactions(commentReactions);
                commentRepository.save(commentEntity);
            }
        }
    }

    public List<ReactionDTO> getAllReactionsByCommentId(Long commentId) {
        commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Comment with id " + commentId + " not found"));
        List<ReactionEntity> reactionEntities = reactionRepository
                .findAllByTargetEntityIdAndTargetEntityTypeEnum(commentId, ReactionTargetTypeEnum.COMMENT);
        return reactionEntities
                .stream()
                .map(reactionEntity -> modelMapper.map(reactionEntity, ReactionDTO.class))
                .collect(Collectors.toList());
    }
}
