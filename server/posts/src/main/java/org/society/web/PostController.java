package org.society.web;

import jakarta.validation.Valid;
import org.society.service.PostService;
import org.society.service.dto.CreatePostDTO;
import org.society.service.dto.PostDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPost(@PathVariable("postId") UUID postId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.getPost(postId));
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestHeader("X-Username") String username,
                                              @Valid @RequestBody CreatePostDTO createPostDTO) {
        PostDTO postDTO = postService.createPost(username, createPostDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postDTO);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<PostDTO> createPost(@PathVariable("postId") UUID postId) {
        postService.deletePost(postId);
        return ResponseEntity
                .status(HttpStatus.OK).build();
    }
}
