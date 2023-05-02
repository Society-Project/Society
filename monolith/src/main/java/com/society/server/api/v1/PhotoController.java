package com.society.server.api.v1;

import com.society.server.dto.photo.PhotoDTO;
import com.society.server.dto.photo.UploadPhotoDTO;
import com.society.server.dto.response.ResponseDTO;
import com.society.server.exception.ResourceNotFoundException;
import com.society.server.service.PhotoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.society.server.config.AppConstants.API_BASE;

@RestController
@RequestMapping(path = API_BASE + "/photos")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("/{photoId}")
    public ResponseEntity<ResponseDTO<PhotoDTO>> getPhotoById(@PathVariable("photoId") Long id){
        try {
            PhotoDTO photoDto = photoService.findPhotoById(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            ResponseDTO
                                    .<PhotoDTO>builder()
                                    .content(photoDto)
                                    .status(HttpStatus.OK.value())
                                    .build()
                    );
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity
                    .status(ex.getStatus())
                    .body(
                            ResponseDTO
                                    .<PhotoDTO>builder()
                                    .message(ex.getMessage())
                                    .status(ex.getStatus().value())
                                    .build()
                    );
        }
    }
    @PostMapping()
    public ResponseEntity<ResponseDTO<Void>> uploadPhoto(@RequestHeader("X-username") String username,
                                                         @Valid @RequestBody UploadPhotoDTO uploadPhotoDTO,
                                                         BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity
                    .badRequest()
                    .body(
                            ResponseDTO
                                    .<Void>builder()
                                    .status(HttpStatus.BAD_REQUEST.value())
                                    .message(errorMessage)
                                    .build()
                    );
        }
        try {
            photoService.uploadPhoto(username, uploadPhotoDTO);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(
                            ResponseDTO
                                    .<Void>builder()
                                    .status(HttpStatus.CREATED.value())
                                    .build()
                    );
        } catch (ResourceNotFoundException ex){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            ResponseDTO
                                    .<Void>builder()
                                    .status(ex.getStatus().value())
                                    .message(ex.getMessage())
                                    .build()
                    );
        }

    }

}
