package com.society.server.api.v1;

import com.society.server.dto.comment.CommentDTO;
import com.society.server.dto.comment.CreateCommentDTO;
import com.society.server.dto.comment.UpdateCommentDTO;
import com.society.server.dto.response.ResponseDTO;
import com.society.server.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.society.server.config.AppConstants.API_BASE;

@RestController
@RequestMapping(path = API_BASE)
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping("/posts/comments")
    public ResponseEntity<ResponseDTO<CommentDTO>> createCommentToPostId(@RequestParam("postId") Long postId,
                                                                         @Valid @RequestBody CreateCommentDTO createCommentDTO,
                                                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorDefaultMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity
                    .badRequest()
                    .body(
                            ResponseDTO
                                    .<CommentDTO>builder()
                                    .status(HttpStatus.BAD_REQUEST.value())
                                    .message(errorDefaultMessage)
                                    .build()
                    );
        }
        CommentDTO commentDTO = commentService.createCommentToPostId(postId, createCommentDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ResponseDTO
                                .<CommentDTO>builder()
                                .content(commentDTO)
                                .status(HttpStatus.CREATED.value())
                                .build()
                );
    }

    @PostMapping("/photos/comments")
    public ResponseEntity<ResponseDTO<CommentDTO>> createCommentToPhotoId(@RequestParam(name = "photoId") Long photoId,
                                                                          @Valid @RequestBody CreateCommentDTO createCommentDTO,
                                                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorDefaultMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity
                    .badRequest()
                    .body(
                            ResponseDTO
                                    .<CommentDTO>builder()
                                    .status(HttpStatus.BAD_REQUEST.value())
                                    .message(errorDefaultMessage)
                                    .build()
                    );
        }
        CommentDTO commentToPhotoId = commentService.createCommentToPhotoId(photoId, createCommentDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ResponseDTO
                                .<CommentDTO>builder()
                                .content(commentToPhotoId)
                                .status(HttpStatus.CREATED.value())
                                .build()
                );
    }

    @DeleteMapping("/posts/comments/{commentId}")
    public ResponseEntity<ResponseDTO<Void>> deleteComment(@PathVariable("commentId") Long commentId) {

        commentService.deleteComment(commentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseDTO
                                .<Void>builder()
                                .status(HttpStatus.OK.value())
                                .build()
                );
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<ResponseDTO<CommentDTO>> updateComment(@PathVariable("commentId") Long commentId,
                                                                 @Valid @RequestBody UpdateCommentDTO updateCommentDTO,
                                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorDefaultMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity
                    .badRequest()
                    .body(
                            ResponseDTO
                                    .<CommentDTO>builder()
                                    .status(HttpStatus.BAD_REQUEST.value())
                                    .message(errorDefaultMessage)
                                    .build()
                    );
        }
        CommentDTO commentDTO = commentService.updateComment(commentId, updateCommentDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseDTO
                                .<CommentDTO>builder()
                                .content(commentDTO)
                                .status(HttpStatus.OK.value())
                                .build()
                );
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<ResponseDTO<CommentDTO>> getComment(@PathVariable("commentId") Long id) {
        CommentDTO commentDto = commentService.getComment(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseDTO
                                .<CommentDTO>builder()
                                .content(commentDto)
                                .status(HttpStatus.OK.value())
                                .build()
                );
    }

    @GetMapping("/posts/comments/postId")
    public ResponseEntity<ResponseDTO<List<CommentDTO>>> getAllCommentsByPostId(@RequestParam("postId") Long postId) {
        List<CommentDTO> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseDTO
                                .<List<CommentDTO>>builder()
                                .status(HttpStatus.OK.value())
                                .content(comments)
                                .build()
                );
    }

    @GetMapping("/comments/photoId")
    public ResponseEntity<ResponseDTO<List<CommentDTO>>> getAllCommentsByPhotoId(@RequestParam("photoId") Long photoId) {
        List<CommentDTO> commentsByPhotoId = commentService.getCommentsByPhotoId(photoId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseDTO
                                .<List<CommentDTO>>builder()
                                .status(HttpStatus.OK.value())
                                .content(commentsByPhotoId)
                                .build()
                );
    }
}
