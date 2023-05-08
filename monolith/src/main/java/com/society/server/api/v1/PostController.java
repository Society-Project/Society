package com.society.server.api.v1;

import com.society.server.dto.post.CreatePostDTO;
import com.society.server.dto.post.PostDTO;
import com.society.server.dto.post.UpdatePostDTO;
import com.society.server.dto.response.ResponseDTO;
import com.society.server.security.IAuthenticationFacade;
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

    @GetMapping("/username")
    public ResponseEntity<ResponseDTO<List<PostDTO>>> getAllPostsByUsername(@RequestParam("username") String username) {
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
    }

    @PostMapping()
    public ResponseEntity<ResponseDTO<PostDTO>> createPost(@Valid @RequestBody CreatePostDTO createPostDTO,
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
        PostDTO postDto = postService.createPost(createPostDTO);
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
    public ResponseEntity<ResponseDTO<PostDTO>> updatePost(@PathVariable(name = "postId") Long id,
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

        PostDTO postDto = postService.updatePost(id, updatePostDTO);
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
    public ResponseEntity<ResponseDTO<Void>> deletePost(@PathVariable("postId") Long postId) {

        postService.deletePost(postId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseDTO
                                .<Void>builder()
                                .status(HttpStatus.OK.value())
                                .build()
                );
    }
}
