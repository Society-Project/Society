package org.society.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.society.repository.PostRepository;
import org.society.repository.entity.PostEntity;
import org.society.service.dto.CreatePostDTO;
import org.society.service.dto.PostDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostService(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    public PostDTO createPost(String username, CreatePostDTO createPostDTO) {
        PostEntity postEntity = postRepository.save(PostEntity
                .builder()
                .creatorUsername(username)
                .textContent(createPostDTO.getTextContent())
                .imageUrl(createPostDTO.getImageUrl())
                .build());
        return modelMapper.map(postEntity, PostDTO.class);
    }

    public void deletePost(UUID postId) {
        postRepository.deleteById(postId);
    }

    @Transactional
    public PostDTO getPost(UUID postId) {
        PostEntity postEntity = postRepository.getReferenceById(postId);
        return modelMapper.map(postEntity, PostDTO.class);
    }
}
