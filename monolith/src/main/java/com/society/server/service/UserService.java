package com.society.server.service;

import com.society.server.dto.user.UserGetPersInfoDTO;
import com.society.server.dto.user.UserProfileDTO;
import com.society.server.dto.user.UserSetPersInfoDTO;
import com.society.server.exception.*;
import com.society.server.model.entity.PhotoEntity;
import com.society.server.model.entity.PostEntity;
import com.society.server.model.entity.user.UserEntity;
import com.society.server.repository.PhotoRepository;
import com.society.server.repository.PostRepository;
import com.society.server.repository.UserRepository;
import com.society.server.security.IAuthenticationFacade;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PhotoRepository photoRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final IAuthenticationFacade authenticationFacade;

    public UserService(UserRepository userRepository, PostRepository postRepository, PhotoRepository photoRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, IAuthenticationFacade authenticationFacade) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.photoRepository = photoRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationFacade = authenticationFacade;
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
        return UserGetPersInfoDTO
                .builder()
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .location(userEntity.getUserPersonalInfo().getLocation())
                .workPlace(userEntity.getUserPersonalInfo().getWorkPlace())
                .education(userEntity.getUserPersonalInfo().getEducation())
                .birthday(userEntity.getUserPersonalInfo().getBirthday())
                .username(userEntity.getUsername())
                .build();

    }

    public void editUserInfo(Long userId, UserSetPersInfoDTO userDto) {
        String authenticatedUserUsername = authenticationFacade.getAuthentication().getName();
        UserEntity userEntity = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "User with id " + userId + " not found"));
        if (!authenticatedUserUsername.equals(userEntity.getUsername())) {
            throw new NotAuthorizedException(HttpStatus.UNAUTHORIZED, "You are not authorized to do that!");
        }
        if (!userDto.getEmail().isBlank()) {
            if (!userDto.getEmail().equals(userEntity.getEmail()))
                throw new BadCredentialsException("Please enter your current email.");
            else if (userDto.getNewEmail().isBlank())
                throw new BadCredentialsException("Please enter your new email.");
            else if (userRepository.existsByEmail(userDto.getNewEmail()))
                throw new BadCredentialsException("User with such email already exists!");
            else userEntity.setEmail(userDto.getNewEmail());
        }
        if (!userDto.getPassword().isBlank()) {
            if (!passwordEncoder.matches(userDto.getPassword(), userEntity.getPassword())) {
                throw new BadCredentialsException("Wrong password!");
            } else {
                userEntity.setPassword(passwordEncoder.encode(userDto.getNewPassword()));
            }
        }
        if (userDto.getBirthday() != null) userEntity.getUserPersonalInfo().setBirthday(userDto.getBirthday());
        if (!userDto.getEducation().isEmpty()) userEntity.getUserPersonalInfo().setEducation(userDto.getEducation());
        if (!userDto.getWorkPlace().isEmpty()) userEntity.getUserPersonalInfo().setWorkPlace(userDto.getWorkPlace());
        if (!userDto.getLocation().isEmpty()) userEntity.getUserPersonalInfo().setLocation(userDto.getLocation());

        userEntity.setUsername(userDto.getUsername());
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userRepository.save(userEntity);
    }
}
