package com.society.server.web;

import com.society.server.dto.post.CreatePostDTO;
import com.society.server.dto.post.PostDTO;
import com.society.server.dto.post.UpdatePostDTO;
import com.society.server.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.society.server.config.AppConstants.API_BASE;

@RestController()
@RequestMapping(path = API_BASE + "/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        // This is probably not a good idea. Maybe we should think about some way to filter posts.
        // What if we have 150 000 000 posts? Pageable?
        List<PostDTO> allPosts = postService.getAllPosts();
        return ResponseEntity.status(HttpStatus.OK).body(allPosts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPost(@PathVariable(name = "postId") Long id) {
        PostDTO post = postService.findPost(id);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @PostMapping()
    public ResponseEntity<PostDTO> createPost(@RequestHeader("X-username") String username,
                                              @Valid @RequestBody CreatePostDTO createPostDTO) {
        PostDTO postDto = postService.createPost(createPostDTO, username);

        return ResponseEntity.status(HttpStatus.CREATED).body(postDto);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> updatePost(@RequestHeader("X-username") String username,
                                              @PathVariable(name = "postId") Long id,
                                              @Valid @RequestBody UpdatePostDTO updatePostDTO) {

        boolean userOwnThePost = postService.isOwner(id, username);
        PostDTO postDto = postService.updatePost(id, updatePostDTO);

        if (!userOwnThePost) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(postDto);
        }
        return ResponseEntity.status(HttpStatus.OK).body(postDto);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@RequestHeader("X-username") String username,
                                           @PathVariable("postId") Long id) {

        postService.deletePost(id, username);
        return ResponseEntity.ok().build();
    }
}
