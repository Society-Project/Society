package com.society.server.service;

import com.society.server.dto.user.UserGetPersInfoDTO;
import com.society.server.dto.user.UserProfileDTO;
import com.society.server.dto.user.UserSetPersInfoDTO;
import com.society.server.exception.*;
import com.society.server.model.entity.PhotoEntity;
import com.society.server.model.entity.PostEntity;
import com.society.server.model.entity.user.UserEntity;
import com.society.server.model.entity.user.UserPersonalInfo;
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

        if (!user.isEnabled()) throw new AccountProblemException(HttpStatus.FORBIDDEN, "Account is not enabled!");
        else if (user.isLocked()) throw new AccountProblemException(HttpStatus.FORBIDDEN, "Account is locked!");
        else if (user.isAccountExpired())
            throw new AccountProblemException(HttpStatus.FORBIDDEN, "Account is expired!");
        return modelMapper.map(user, UserProfileDTO.class);
    }


    public UserGetPersInfoDTO getUserInfoById(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(HttpStatus.NOT_FOUND, "User with id " + userId + " not found"));
        UserPersonalInfo userPersonalInfo = userEntity.getUserPersonalInfo();

        if (userPersonalInfo == null) {

            return UserGetPersInfoDTO
                    .builder()
                    .firstName(userEntity.getFirstName())
                    .lastName(userEntity.getLastName())
                    .email(userEntity.getEmail())
                    .username(userEntity.getUsername())
                    .build();
        } else {
            return UserGetPersInfoDTO
                    .builder()
                    .firstName(userEntity.getFirstName())
                    .lastName(userEntity.getLastName())
                    .location(userEntity.getUserPersonalInfo().getLocation())
                    .workPlace(userEntity.getUserPersonalInfo().getWorkPlace())
                    .education(userEntity.getUserPersonalInfo().getEducation())
                    .birthday(userEntity.getUserPersonalInfo().getBirthday())
                    .email(userEntity.getEmail())
                    .username(userEntity.getUsername())
                    .build();
        }

    }

    /*public void editUserInfo(Long userId, UserSetPersInfoDTO userDto) {
        UserEntity userEntity = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "User with id " + userId + " not found"));
        UserPersonalInfo personalInfo = new UserPersonalInfo();
        personalInfo.setBirthday(userDto.getBirthday());
        personalInfo.setEducation(userDto.getEducation());
        personalInfo.setLocation(userDto.getLocation());
        personalInfo.setWorkPlace(userDto.getWorkPlace());

        userEntity.setUserPersonalInfo(personalInfo);

        userEntity.setUsername(userDto.getUsername());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.

    }*/
}
