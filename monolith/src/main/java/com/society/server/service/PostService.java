package com.society.server.service;

import com.society.server.dto.post.CreatePostDTO;
import com.society.server.dto.post.PostDTO;
import com.society.server.dto.post.UpdatePostDTO;
import com.society.server.dto.reaction.ReactionDTO;
import com.society.server.exception.NotAuthorizedException;
import com.society.server.exception.ResourceNotFoundException;
import com.society.server.model.entity.CommentEntity;
import com.society.server.model.entity.PostEntity;
import com.society.server.model.entity.ReactionEntity;
import com.society.server.model.entity.user.UserEntity;
import com.society.server.model.enums.ReactionTargetTypeEnum;
import com.society.server.repository.CommentRepository;
import com.society.server.repository.PostRepository;
import com.society.server.repository.ReactionRepository;
import com.society.server.repository.UserRepository;
import com.society.server.security.IAuthenticationFacade;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final IAuthenticationFacade authenticationFacade;
    private final ReactionRepository reactionRepository;

    public PostService(PostRepository postRepository, ModelMapper modelMapper, UserRepository userRepository, CommentRepository commentRepository, IAuthenticationFacade authenticationFacade, ReactionRepository reactionRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.authenticationFacade = authenticationFacade;
        this.reactionRepository = reactionRepository;
    }

    //Return the last 50 posts sorted by creation time and cache them.
    //Thus, on the next request, it will not be necessary to access database.
    @Cacheable(value = "postsCache")
    public List<PostDTO> getAllPosts() {
        List<PostEntity> allPosts = postRepository.findTop50ByOrderByCreatedOnDesc();
        List<PostDTO> postDTOS = new ArrayList<>();
        for (PostEntity p : allPosts) {
            List<CommentEntity> commentEntities = commentRepository.findAllByPostId(p.getId());
            List<ReactionEntity> reactionEntities = reactionRepository.findAllByTargetEntityIdAndTargetEntityTypeEnum(p.getId(), ReactionTargetTypeEnum.POST);
            p.setReactions(reactionEntities);
            for (CommentEntity commentEntity : commentEntities) {
                List<ReactionEntity> commentReactions = reactionRepository
                        .findAllByTargetEntityIdAndTargetEntityTypeEnum(commentEntity.getId(), ReactionTargetTypeEnum.COMMENT);
                commentEntity.setReactions(commentReactions);
            }
            p.setComments(commentEntities);
            PostDTO postDTO = modelMapper.map(p, PostDTO.class);
            postDTOS.add(postDTO);
        }
        return postDTOS;
    }

    public PostDTO findPost(Long id) {
        PostEntity postEntity = postRepository
                .findPostEntityById(id)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Post with id " + id + " not exist."));
        List<ReactionEntity> reactionsByPostId = reactionRepository
                .findAllByTargetEntityIdAndTargetEntityTypeEnum(id, ReactionTargetTypeEnum.POST);

        List<CommentEntity> commentsByPostId = commentRepository.findAllByPostId(id);
        for (CommentEntity commentEntity : commentsByPostId) {
            commentEntity.setReactions(reactionRepository
                    .findAllByTargetEntityIdAndTargetEntityTypeEnum(id, ReactionTargetTypeEnum.COMMENT));
        }
        postEntity.setReactions(reactionsByPostId);
        postEntity.setComments(commentsByPostId);
        return modelMapper.map(postEntity, PostDTO.class);
    }

    public PostDTO createPost(CreatePostDTO createPostDTO) {
        String username = authenticationFacade.getAuthentication().getName();

        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException(HttpStatus.NOT_FOUND, "User with username " + username + " not found."));
        PostEntity postEntity = modelMapper.map(createPostDTO, PostEntity.class);
        postEntity.setAuthorUsername(username);

        List<PostEntity> authorPosts = postRepository.findAllByAuthorUsername(username);
        authorPosts.add(postEntity);
        postRepository.save(postEntity);
        userEntity.setUserPosts(authorPosts);
        userRepository.save(userEntity);
        return modelMapper.map(postEntity, PostDTO.class);
    }

    public PostDTO updatePost(Long id, UpdatePostDTO updatePostDTO) {
        String username = authenticationFacade.getAuthentication().getName();
        PostEntity postEntity = postRepository
                .findPostEntityById(id)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Post with id " + id + " not exist."));
        if (!isOwner(id, username))
            throw new NotAuthorizedException(HttpStatus.UNAUTHORIZED, "You can only update posts written by you!");
        List<CommentEntity> commentsByPostId = commentRepository.findAllByPostId(postEntity.getId());
        for (CommentEntity commentEntity : commentsByPostId) {
            List<ReactionEntity> reactionsByComment = reactionRepository
                    .findAllByTargetEntityIdAndTargetEntityTypeEnum(commentEntity.getId(), ReactionTargetTypeEnum.COMMENT);
            commentEntity.setReactions(reactionsByComment);
        }

        postEntity.setTextContent(updatePostDTO.getTextContent());
        postEntity.setImageUrl(updatePostDTO.getImageUrl());
        postEntity.setUpdatedOn(LocalDateTime.now());
        postEntity.setComments(commentsByPostId);
        postEntity.setReactions(reactionRepository
                .findAllByTargetEntityIdAndTargetEntityTypeEnum(postEntity.getId(), ReactionTargetTypeEnum.POST));
        postRepository.save(postEntity);

        return modelMapper.map(postEntity, PostDTO.class);
    }

    @Transactional
    public void deletePost(Long postId) {
        String username = authenticationFacade.getAuthentication().getName();
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException(HttpStatus.NOT_FOUND, "User with username " + username + " not found."));
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Post with id " + postId + " not found."));

        if (isAdmin(userEntity) || isOwner(postId, username)) {
            userEntity.getUserPosts().remove(postEntity);
            postRepository.delete(postEntity);
            userRepository.save(userEntity);
        } else {
            throw new NotAuthorizedException("You are not authorized to delete this post.");
        }
    }

    public boolean isOwner(Long id, String username) {
        PostEntity postEntity = postRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        String authorUsername = postEntity.getAuthorUsername();
        return authorUsername.equals(username);
    }

    public boolean isAdmin(UserEntity userEntity) {
        return userEntity.getRoles().stream()
                .anyMatch(role -> role.getRoleName().name().equals("ADMIN"));
    }

    public List<PostDTO> getAllPostsByUsername(String username) {
        if (!userRepository.existsByUsername(username))
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "User with username " + username + " not found.");
        List<PostEntity> postsByUsername = postRepository.findAllByAuthorUsername(username);
        for (PostEntity postEntity : postsByUsername) {
            List<ReactionEntity> reactionsByPostId = reactionRepository
                    .findAllByTargetEntityIdAndTargetEntityTypeEnum(postEntity.getId(), ReactionTargetTypeEnum.POST);
            List<CommentEntity> commentsByPostId = commentRepository.findAllByPostId(postEntity.getId());
            for (CommentEntity commentEntity : commentsByPostId) {
                List<ReactionEntity> commentReactions = reactionRepository
                        .findAllByTargetEntityIdAndTargetEntityTypeEnum(commentEntity.getId(), ReactionTargetTypeEnum.COMMENT);
                commentEntity.setReactions(commentReactions);
            }
            postEntity.setReactions(reactionsByPostId);
            postEntity.setComments(commentsByPostId);
        }
        return postsByUsername.stream().map(postEntity -> modelMapper.map(postEntity, PostDTO.class)).collect(Collectors.toList());
    }

    public void reactToPost(Long postId, ReactionDTO reactionDTO) {
        UserEntity userEntity = userRepository.findByUsername(authenticationFacade.getAuthentication().getName())
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "User not found"));
        ReactionEntity reactionEntity = reactionRepository
                .findByTargetEntityIdAndRespondingUserIdAndTargetEntityTypeEnum(postId, userEntity.getId(), ReactionTargetTypeEnum.POST);
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Post with id " + postId + " not found"));
        if (reactionEntity != null) {
            if (reactionDTO.getReactionType() == null) {
                List<ReactionEntity> postReactions = reactionRepository
                        .findAllByTargetEntityIdAndTargetEntityTypeEnum(postId, ReactionTargetTypeEnum.POST);
                postReactions.remove(reactionEntity);
                postEntity.setReactions(postReactions);
                postRepository.save(postEntity);
                reactionRepository.delete(reactionEntity);
            } else {
                reactionEntity.setReactionType(reactionDTO.getReactionType());
                reactionRepository.save(reactionEntity);
            }
        } else {
            if (reactionDTO.getReactionType() != null) {
                List<ReactionEntity> postReactions = reactionRepository
                        .findAllByTargetEntityIdAndTargetEntityTypeEnum(postId, ReactionTargetTypeEnum.POST);
                ReactionEntity newReaction = new ReactionEntity(reactionDTO.getReactionType(), userEntity.getId(), postId, ReactionTargetTypeEnum.POST);
                reactionRepository.save(newReaction);
                postReactions.add(newReaction);
                postEntity.setReactions(postReactions);
                postRepository.save(postEntity);
            }
        }
    }

    public List<ReactionDTO> getAllReactionsByPostId(Long postId) {
        postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Post with id " + postId + " not found"));
        List<ReactionEntity> reactionEntities = reactionRepository
                .findAllByTargetEntityIdAndTargetEntityTypeEnum(postId, ReactionTargetTypeEnum.POST);
        return reactionEntities
                .stream()
                .map(reactionEntity -> modelMapper.map(reactionEntity, ReactionDTO.class))
                .collect(Collectors.toList());
    }
}
