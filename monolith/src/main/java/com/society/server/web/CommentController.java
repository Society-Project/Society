package com.society.server.web;

import com.society.server.dto.comment.CommentDTO;
import com.society.server.dto.comment.CreateCommentDTO;
import com.society.server.dto.comment.UpdateCommentDTO;
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
                                                    @RequestParam("postId") Long id,
                                                    @Valid @RequestBody CreateCommentDTO createCommentDTO) {
        CommentDTO commentDTO = commentService.createComment(id, username, createCommentDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentDTO);
    }

    @DeleteMapping("/posts/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@RequestHeader("X-username") String username,
                                              @RequestParam("postId") Long postId,
                                              @PathVariable("commentId") Long id) {
        commentService.deleteComment(username, postId, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PutMapping("/posts/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@RequestHeader("X-username") String username,
                                                    @RequestParam("postId") Long postId,
                                                    @PathVariable("commentId") Long id,
                                                    @Valid @RequestBody UpdateCommentDTO updateCommentDTO) {

        CommentDTO commentDTO = commentService.updateComment(postId,username, id, updateCommentDTO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentDTO);
    }

    @GetMapping("/posts/comments/{commentId}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable("commentId") Long id) {
        CommentDTO commentDto = commentService.getComment(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentDto);
    }

    @GetMapping("/posts/comments")
    public ResponseEntity<List<CommentDTO>> getAllCommentsByPostId(@RequestParam("postId") Long postId) {
        List<CommentDTO> comments = commentService.getCommentsByPostId(postId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments);
    }
}
