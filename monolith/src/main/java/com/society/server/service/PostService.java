package com.society.server.service;

import com.society.server.dto.post.CreatePostDTO;
import com.society.server.dto.post.PostDTO;
import com.society.server.dto.post.UpdatePostDTO;
import com.society.server.exception.NotAuthorizedException;
import com.society.server.exception.ResourceNotFoundException;
import com.society.server.model.entity.PostEntity;
import com.society.server.model.entity.RoleEntity;
import com.society.server.model.entity.UserEntity;
import com.society.server.repository.PostRepository;
import com.society.server.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public List<PostDTO> getAllPosts() {
        List<PostEntity> allPosts = postRepository.findAll();
        return allPosts.stream().map(p -> modelMapper.map(p, PostDTO.class)).collect(Collectors.toList());
    }

    public PostDTO findPost(Long id){
        PostEntity postEntity = postRepository
                .findPostEntityById(id)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Post with id " + id + " not exist."));
        return modelMapper.map(postEntity, PostDTO.class);
    }

    public PostDTO createPost(CreatePostDTO createPostDTO, String username) {
        if(userRepository.findByUsername(username).isEmpty()) {
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "User with username \"" + username +  "\" not exist.");
        }
        PostEntity postEntity = modelMapper.map(createPostDTO, PostEntity.class);
        postEntity.setAuthorUsername(username);
        postRepository.save(postEntity);

        return modelMapper.map(postEntity, PostDTO.class);
    }

    public PostDTO updatePost(Long id, UpdatePostDTO updatePostDTO) {
        PostEntity postEntity = postRepository
                .findPostEntityById(id)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Post with id " + id + " not exist."));

        postEntity.setTextContent(updatePostDTO.getTextContent())
                .setImageUrl(updatePostDTO.getImageUrl());
        postRepository.save(postEntity);

        return modelMapper.map(postEntity, PostDTO.class);
    }

    public void deletePost(Long id, String username){
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(ResourceNotFoundException::new);
        PostEntity postEntity = postRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        if(isAdmin(userEntity) || postEntity.getAuthorUsername().equals(username)) {
            postRepository.delete(postEntity);
        } else {
            throw new NotAuthorizedException("You are not authorized to delete this post.");
        }
    }

    public boolean isOwner(Long id, String username) {
        PostEntity postEntity = postRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        String authorUsername = postEntity.getAuthorUsername();
        return authorUsername.equals(username);
    }

    public boolean isAdmin(UserEntity userEntity){
        return userEntity.getRoles().stream()
                .anyMatch(role -> role.getRoleName().name().equals("ADMIN"));
    }

}
