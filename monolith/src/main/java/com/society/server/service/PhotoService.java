package com.society.server.service;

import com.society.server.dto.photo.PhotoDTO;
import com.society.server.dto.photo.UploadPhotoDTO;
import com.society.server.exception.NotAuthorizedException;
import com.society.server.exception.ResourceNotFoundException;
import com.society.server.model.entity.CommentEntity;
import com.society.server.model.entity.PhotoEntity;
import com.society.server.model.entity.user.UserEntity;
import com.society.server.repository.CommentRepository;
import com.society.server.repository.PhotoRepository;
import com.society.server.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PhotoService {
    private final PostService postService;
    private final PhotoRepository photoRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public PhotoService(PostService postService, PhotoRepository photoRepository, CommentRepository commentRepository,
                        UserRepository userRepository,
                        ModelMapper modelMapper) {
        this.postService = postService;
        this.photoRepository = photoRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public PhotoDTO findPhotoById(Long id) {
        PhotoEntity photoEntity = photoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Photo with id " + id + " not found!"));
        List<CommentEntity> commentsByPhotoId = commentRepository.findCommentEntityByPhotoId(id);
        photoEntity.setComments(commentsByPhotoId);

        return modelMapper.map(photoEntity, PhotoDTO.class);
    }

    public void uploadPhoto(String username, UploadPhotoDTO uploadPhotoDTO) {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND,
                "User with username " + username + " not found."));

        List<PhotoEntity> allPhotosByUsername = photoRepository.findAllByPhotoOwner(username);

        PhotoEntity photoEntity = modelMapper.map(uploadPhotoDTO, PhotoEntity.class);
        photoEntity.setPhotoOwner(username);
        photoRepository.save(photoEntity);
        allPhotosByUsername.add(photoEntity);
        userEntity.setUserPhotos(allPhotosByUsername);
        userRepository.save(userEntity);
    }
    @Transactional
    public void deletePhotoById(String username, Long photoId) {
        PhotoEntity photoEntity = photoRepository.findById(photoId).orElseThrow(() ->
                new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Photo with id " + photoId + " not found."));
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND,
                "User with username " + username + " not found."));

        if(!postService.isAdmin(userEntity) && !photoEntity.getPhotoOwner().equals(username)) {
            throw new NotAuthorizedException("You are not authorized to delete this photo.");
        } else {
            List<PhotoEntity> userPhotos = photoRepository.findAllByPhotoOwner(username);
            userPhotos.remove(photoEntity);
            userEntity.setUserPhotos(userPhotos);
            userRepository.save(userEntity);
            photoRepository.delete(photoEntity);
        }
    }
}
