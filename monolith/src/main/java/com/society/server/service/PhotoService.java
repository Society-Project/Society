package com.society.server.service;

import com.society.server.dto.photo.PhotoDTO;
import com.society.server.exception.ResourceNotFoundException;
import com.society.server.model.entity.CommentEntity;
import com.society.server.model.entity.PhotoEntity;
import com.society.server.repository.CommentRepository;
import com.society.server.repository.PhotoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    public PhotoService(PhotoRepository photoRepository, CommentRepository commentRepository, ModelMapper modelMapper) {
        this.photoRepository = photoRepository;
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    public PhotoDTO findPhotoById(Long id) {
        PhotoEntity photoEntity = photoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Photo with id " + id + " not found!"));
        List<CommentEntity> commentsByPhotoId = commentRepository.findCommentEntityByPhotoId(id);
        photoEntity.setComments(commentsByPhotoId);

        return modelMapper.map(photoEntity, PhotoDTO.class);
    }
}
