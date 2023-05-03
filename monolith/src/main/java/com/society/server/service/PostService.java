package com.society.server.service;

import com.society.server.dto.post.CreatePostDTO;
import com.society.server.dto.post.PostDTO;
import com.society.server.dto.post.UpdatePostDTO;
import com.society.server.exception.NotAuthorizedException;
import com.society.server.exception.ResourceNotFoundException;
import com.society.server.model.entity.CommentEntity;
import com.society.server.model.entity.PostEntity;
import com.society.server.model.entity.user.UserEntity;
import com.society.server.repository.CommentRepository;
import com.society.server.repository.PostRepository;
import com.society.server.repository.UserRepository;
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

    public PostService(PostRepository postRepository, ModelMapper modelMapper, UserRepository userRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }
    //Return the last 50 posts sorted by creation time and cache them.
    //Thus, on the next request, it will not be necessary to access database.
    @Cacheable(value = "postsCache")
    public List<PostDTO> getAllPosts() {
        List<PostEntity> allPosts = postRepository.findTop50ByOrderByCreatedOnDesc();
        List<PostDTO> postDTOS = new ArrayList<>();
        for (PostEntity p : allPosts) {
            List<CommentEntity> commentEntities = new ArrayList<>(commentRepository.findAllByPostId(p.getId()));
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
        return modelMapper.map(postEntity, PostDTO.class);
    }

    public PostDTO createPost(CreatePostDTO createPostDTO, String username) {
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

    public PostDTO updatePost(Long id, UpdatePostDTO updatePostDTO, String username) {
        PostEntity postEntity = postRepository
                .findPostEntityById(id)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Post with id " + id + " not exist."));
        if(!isOwner(id, username)) throw new NotAuthorizedException(HttpStatus.UNAUTHORIZED, "You can update only posts written by you!");

        postEntity.setTextContent(updatePostDTO.getTextContent())
                .setImageUrl(updatePostDTO.getImageUrl())
                        .setUpdatedOn(LocalDateTime.now());
        postRepository.save(postEntity);

        return modelMapper.map(postEntity, PostDTO.class);
    }

    @Transactional
    public void deletePost(Long id, String username) {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException(HttpStatus.NOT_FOUND, "User with username " + username + " not found."));
        PostEntity postEntity = postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Post with id " + id + " not found."));

        if (isAdmin(userEntity) || postEntity.getAuthorUsername().equals(username)) {
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
        List<PostEntity> postsByUsername = postRepository.findAllByAuthorUsername(username);
        return postsByUsername.stream().map(postEntity -> modelMapper.map(postEntity, PostDTO.class)).collect(Collectors.toList());
    }
}
