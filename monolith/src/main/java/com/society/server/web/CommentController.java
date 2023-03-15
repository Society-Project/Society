package com.society.server.web;

import com.society.server.dto.comment.CommentDTO;
import com.society.server.dto.comment.CreateCommentDTO;
import com.society.server.dto.comment.UpdateCommentDTO;
import com.society.server.service.CommentService;
import com.society.server.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.society.server.config.AppConstants.API_BASE;

@RestController
@RequestMapping(path = API_BASE + "/comments")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }


    @PostMapping("/comment")
    public ResponseEntity<CommentDTO> createComment(@RequestHeader("X-username") String username,
                                                    @RequestParam("postId") Long id,
                                                    @Valid @RequestBody CreateCommentDTO createCommentDTO) {
        CommentDTO commentDTO = commentService.createComment(id, username, createCommentDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentDTO);
    }

    //Todo: Fix deleteComment method
    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentDTO> deleteComment(@RequestHeader("X-username") String username,
                                                    @PathVariable("commentId") Long id) {
        commentService.deleteComment(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    //Todo: Fix updateComment method
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable("commentId") Long id,
            @Valid @RequestBody UpdateCommentDTO updateCommentDTO) {

        CommentDTO commentDTO = commentService.updateComment(id, updateCommentDTO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentDTO);
    }
    //Todo: Implement GetComment(), etc...
}
