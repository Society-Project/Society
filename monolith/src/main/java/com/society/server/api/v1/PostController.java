package com.society.server.api.v1;

import com.society.server.dto.post.CreatePostDTO;
import com.society.server.dto.post.PostDTO;
import com.society.server.dto.post.UpdatePostDTO;
import com.society.server.dto.response.ResponseDTO;
import com.society.server.exception.NotAuthorizedException;
import com.society.server.exception.ResourceNotFoundException;
import com.society.server.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<ResponseDTO<List<PostDTO>>> getAllPosts() {
        List<PostDTO> allPosts = postService.getAllPosts();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseDTO
                                .<List<PostDTO>>builder()
                                .status(HttpStatus.OK.value())
                                .content(allPosts)
                                .build()
                );
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<ResponseDTO<List<PostDTO>>> getAllPostsByUsername(
                                         @PathVariable("username") String username) {
        List<PostDTO> postsByUsername = postService.getAllPostsByUsername(username);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseDTO
                                .<List<PostDTO>>builder()
                                .status(HttpStatus.OK.value())
                                .content(postsByUsername)
                                .build()
                );
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ResponseDTO<PostDTO>> getPost(@PathVariable(name = "postId") Long id) {
        try {
            PostDTO post = postService.findPost(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            ResponseDTO
                                    .<PostDTO>builder()
                                    .content(post)
                                    .status(HttpStatus.OK.value())
                                    .build()
                    );
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            ResponseDTO
                                    .<PostDTO>builder()
                                    .message(ex.getMessage())
                                    .status(ex.getStatus().value())
                                    .build()
                    );
        }

    }

    @PostMapping()
    public ResponseEntity<ResponseDTO<PostDTO>> createPost(@RequestHeader("X-username") String username,
                                                           @Valid @RequestBody CreatePostDTO createPostDTO,
                                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(
                            ResponseDTO
                                    .<PostDTO>builder()
                                    .status(HttpStatus.BAD_REQUEST.value())
                                    .message("Post can not be empty!")
                                    .build()
                    );
        }
        PostDTO postDto = postService.createPost(createPostDTO, username);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ResponseDTO
                                .<PostDTO>builder()
                                .content(postDto)
                                .status(HttpStatus.CREATED.value())
                                .build()
                );
    }

    @PutMapping("/{postId}")
    public ResponseEntity<ResponseDTO<PostDTO>> updatePost(@RequestHeader("X-username") String username,
                                                           @PathVariable(name = "postId") Long id,
                                                           @Valid @RequestBody UpdatePostDTO updatePostDTO,
                                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(
                            ResponseDTO
                                    .<PostDTO>builder()
                                    .status(HttpStatus.BAD_REQUEST.value())
                                    .message("Post can not be empty!")
                                    .build()
                    );
        }

        boolean userOwnThePost = postService.isOwner(id, username);
        PostDTO postDto = postService.updatePost(id, updatePostDTO);

        if (!userOwnThePost) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(
                            ResponseDTO
                                    .<PostDTO>builder()
                                    .status(HttpStatus.FORBIDDEN.value())
                                    .message("You can update only posts written by you!")
                                    .build()
                    );
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseDTO
                                .<PostDTO>builder()
                                .content(postDto)
                                .status(HttpStatus.OK.value())
                                .build()
                );
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ResponseDTO<Void>> deletePost(@RequestHeader("X-username") String username,
                                                        @PathVariable("postId") Long id) {

        try {
            postService.deletePost(id, username);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            ResponseDTO
                                    .<Void>builder()
                                    .status(HttpStatus.OK.value())
                                    .build()
                    );
        } catch (ResourceNotFoundException | NotAuthorizedException ex) {
            return ResponseEntity
                    .status(ex.getStatus())
                    .body(
                            ResponseDTO
                                    .<Void>builder()
                                    .message(ex.getMessage())
                                    .status(ex.getStatus().value())
                                    .build()
                    );
        }
    }
}
