package com.society.server.api.v1;

import com.society.server.dto.comment.CommentDTO;
import com.society.server.dto.comment.CreateCommentDTO;
import com.society.server.dto.comment.UpdateCommentDTO;
import com.society.server.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.society.server.config.AppConstants.API_BASE;

@RestController
@RequestMapping(path = API_BASE + "/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping()
    public ResponseEntity<CommentDTO> createComment(@RequestHeader("X-username") String username,
                                                    @Valid @RequestBody CreateCommentDTO createCommentDTO) {
        CommentDTO commentDTO = commentService.createComment(username, createCommentDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentDTO);
    }

    @DeleteMapping("/commentId")
    public ResponseEntity<CommentDTO> deleteComment(@PathVariable("commentId") Long id) {
        commentService.deleteComment(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PatchMapping("/update/commentId")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable("commentId") Long id,
            @Valid @RequestBody UpdateCommentDTO updateCommentDTO) {

        CommentDTO commentDTO = commentService.updateComment(id, updateCommentDTO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentDTO);
    }

}
