package com.society.server.api.v1;

import com.society.server.dto.comment.CommentDTO;
import com.society.server.dto.comment.CreateCommentDTO;
import com.society.server.dto.comment.UpdateCommentDTO;
import com.society.server.dto.response.ResponseDTO;
import com.society.server.exception.ResourceNotFoundException;
import com.society.server.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CommentDTO> createComment(@RequestHeader("X-username") String username,
                                                    @RequestParam("postId") Long postId,
                                                    @Valid @RequestBody CreateCommentDTO createCommentDTO) {
        CommentDTO commentDTO = commentService.createComment(postId, username, createCommentDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentDTO);
    }

    @DeleteMapping("/posts/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@RequestHeader("X-username") String username,
                                              @PathVariable("commentId") Long id) {
        commentService.deleteComment(username, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PutMapping("/posts/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@RequestHeader("X-username") String username,
                                                    @PathVariable("commentId") Long id,
                                                    @Valid @RequestBody UpdateCommentDTO updateCommentDTO) {

        CommentDTO commentDTO = commentService.updateComment(username, id, updateCommentDTO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentDTO);
    }

    @GetMapping("/posts/comments/{commentId}")
    public ResponseEntity<ResponseDTO<CommentDTO>> getComment(@PathVariable("commentId") Long id) {
        try {
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
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity
                    .status(ex.getStatus())
                    .body(
                            ResponseDTO
                                    .<CommentDTO>builder()
                                    .message(ex.getMessage())
                                    .status(ex.getStatus().value())
                                    .build()
                    );
        }
    }

    @GetMapping("/posts/comments")
    public ResponseEntity<List<CommentDTO>> getAllCommentsByPostId(@RequestParam("postId") Long postId) {
        List<CommentDTO> comments = commentService.getCommentsByPostId(postId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments);
    }
}
