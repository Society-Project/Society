package org.society.comments.web;

import jakarta.validation.Valid;
import org.society.comments.model.dto.CommentDto;
import org.society.comments.model.dto.CreateCommentDTO;
import org.society.comments.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestHeader("X-userName") String username,
                                                    @Valid @RequestBody CreateCommentDTO createCommentDTO) {
        CommentDto commentDto = commentService.createComment(username, createCommentDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentDto);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentDto> deleteComment(@PathVariable("commentId") UUID id) {
        commentService.deletePost(id);

       return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
