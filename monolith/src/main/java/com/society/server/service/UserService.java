package com.society.server.service;

import com.society.server.dto.user.UserProfileDTO;
import com.society.server.exception.*;
import com.society.server.model.entity.PhotoEntity;
import com.society.server.model.entity.PostEntity;
import com.society.server.model.entity.user.UserEntity;
import com.society.server.repository.PhotoRepository;
import com.society.server.repository.PostRepository;
import com.society.server.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PhotoRepository photoRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, PostRepository postRepository, PhotoRepository photoRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.photoRepository = photoRepository;
        this.modelMapper = modelMapper;
    }

    public UserProfileDTO getUserById(Long userId) {
        UserEntity user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "The user is not found."));
        List<PostEntity> userPosts = postRepository.findAllByAuthorUsername(user.getUsername());
        List<PhotoEntity> userPhotos = photoRepository.findAllByPhotoOwner(user.getUsername());
        user.setUserPosts(userPosts);
        user.setUserPhotos(userPhotos);

        if(!user.isEnabled()) throw new AccountProblemException(HttpStatus.FORBIDDEN, "Account is not enabled!");
        else if(user.isLocked()) throw new AccountProblemException(HttpStatus.FORBIDDEN, "Account is locked!");
        else if(user.isAccountExpired()) throw new AccountProblemException(HttpStatus.FORBIDDEN, "Account is expired!");
        return modelMapper.map(user, UserProfileDTO.class);
    }


}
