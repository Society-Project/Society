package com.society.server.service;

import com.society.server.dto.post.CreatePostDTO;
import com.society.server.dto.post.PostDTO;
import com.society.server.dto.post.UpdatePostDTO;
import com.society.server.exception.NotAuthorizedException;
import com.society.server.exception.ResourceNotFoundException;
import com.society.server.model.entity.PostEntity;
import com.society.server.model.entity.RoleEntity;
import com.society.server.model.entity.user.UserEntity;
import com.society.server.model.enums.RoleEnum;
import com.society.server.repository.CommentRepository;
import com.society.server.repository.PostRepository;
import com.society.server.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private CommentRepository commentRepository;
    private PostService underTest;

    @BeforeEach
    void setUp() {
        underTest = new PostService(postRepository, modelMapper, userRepository, commentRepository);
    }

    //@Test
    void canGetAllPosts() {
        underTest.getAllPosts();
        verify(postRepository).findAll();
    }

    //@Test
    void findPostSuccessful() {
        PostEntity postEntity = new PostEntity();
        postEntity.setId(1L);
        postEntity.setTextContent("Test Content");
        postEntity.setComments(new ArrayList<>());
        postEntity.setAuthorUsername("TestName");
        postEntity.setImageUrl(null);

        PostDTO postDTO = new PostDTO();
        postDTO.setId(1L);
        postDTO.setTextContent("Test Content");
        postDTO.setComments(new ArrayList<>());
        postDTO.setAuthorUsername("TestName");
        postDTO.setImageUrl(null);

        when(postRepository.findPostEntityById(anyLong())).thenReturn(Optional.of(postEntity));
        when(modelMapper.map(postEntity, PostDTO.class)).thenReturn(postDTO);

        PostDTO result = underTest.findPost(1L);

        assertNotNull(result);
        assertEquals(postDTO.getId(), result.getId());
        assertEquals(postDTO.getAuthorUsername(), result.getAuthorUsername());
        assertEquals(postDTO.getTextContent(), result.getTextContent());
    }

    //@Test()
    void findPostThrownNotFoundException() {
        when(postRepository.findPostEntityById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> underTest.findPost(1L), "Post with id 1 not exist.");
    }

    //@Test
    void createPostSuccessful() {
        CreatePostDTO createPostDTO = new CreatePostDTO();
        createPostDTO.setTextContent("Test Post");
        createPostDTO.setImageUrl("Test URL");

        String username = "testuser";
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);

        PostEntity postEntity = new PostEntity();

        postEntity.setId(1L);
        postEntity.setImageUrl(createPostDTO.getImageUrl());
        postEntity.setTextContent(createPostDTO.getTextContent());
        postEntity.setAuthorUsername(username);

        PostDTO expectedPostDTO = new PostDTO();

        expectedPostDTO.setId(postEntity.getId());
        expectedPostDTO.setImageUrl(postEntity.getImageUrl());
        expectedPostDTO.setTextContent(postEntity.getTextContent());
        expectedPostDTO.setAuthorUsername(postEntity.getAuthorUsername());

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(modelMapper.map(createPostDTO, PostEntity.class)).thenReturn(postEntity);
        when(postRepository.save(postEntity)).thenReturn(postEntity);
        when(modelMapper.map(postEntity, PostDTO.class)).thenReturn(expectedPostDTO);

        PostDTO actualPostDTO = underTest.createPost(createPostDTO, username);

        assertNotNull(actualPostDTO);
        assertEquals(expectedPostDTO.getId(), actualPostDTO.getId());
        assertEquals(expectedPostDTO.getImageUrl(), actualPostDTO.getImageUrl());
        assertEquals(expectedPostDTO.getTextContent(), actualPostDTO.getTextContent());
        assertEquals(expectedPostDTO.getAuthorUsername(), actualPostDTO.getAuthorUsername());
        assertEquals(expectedPostDTO.getComments(), actualPostDTO.getComments());
    }

    //@Test
    void createPostThrownUserNotFoundException(){
        CreatePostDTO createPostDTO = new CreatePostDTO();
        createPostDTO.setImageUrl("Test Post");
        createPostDTO.setTextContent("This is a test post");

        String username = "testuser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> underTest.createPost(createPostDTO, username),
                "User with username \"testuser\" not exist.");
    }

    //@Test
    void updatePostSuccessful() {
        UpdatePostDTO updatePostDTO = new UpdatePostDTO("Updated Text", "Updated URL");
        PostEntity postEntity = new PostEntity();
        postEntity.setImageUrl("Old URL").setTextContent("Old Text").setId(1L);
        when(postRepository.findPostEntityById(1L)).thenReturn(Optional.of(postEntity));

        PostDTO expectedUpdatedDTO = new PostDTO();
        expectedUpdatedDTO.setId(1L).setImageUrl(updatePostDTO.getImageUrl()).setImageUrl(updatePostDTO.getImageUrl());
        when(modelMapper.map(postEntity, PostDTO.class)).thenReturn(expectedUpdatedDTO);

        PostDTO actualPostDTO = underTest.updatePost(1L, updatePostDTO);

        assertThat(actualPostDTO).isEqualTo(expectedUpdatedDTO);
        verify(postRepository).save(postEntity);
    }

    //@Test
    void updatePostThrownNotFoundException() {
        UpdatePostDTO updatePostDTO = new UpdatePostDTO();

        when(postRepository.findPostEntityById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> underTest.updatePost(1L, updatePostDTO),
                "Post with id 1 not exist.");
    }

    //@Test
    void deletePostSuccessfulWithUserRole() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleName(RoleEnum.USER);

        String testUsername = "Test username";

        UserEntity user = new UserEntity();
        user.setUsername(testUsername);
        user.setRoles(Set.of(roleEntity));

        PostEntity post = new PostEntity();
        long testId = 1L;
        post.setId(testId);
        post.setAuthorUsername(testUsername);

        when(userRepository.findByUsername(testUsername)).thenReturn(Optional.of(user));
        when(postRepository.findById(testId)).thenReturn(Optional.of(post));

        underTest.deletePost(testId, testUsername);

        verify(postRepository).delete(post);
    }

    //@Test
    void deletePostSuccessfulWithAdminRole() {
        RoleEntity adminRole = new RoleEntity();
        adminRole.setRoleName(RoleEnum.ADMIN);

        String userUsername = "User username";
        String adminUsername = "Admin username";

        UserEntity user = new UserEntity();
        user.setUsername(userUsername);
        user.setRoles(Set.of(new RoleEntity(RoleEnum.USER)));

        UserEntity admin = new UserEntity();
        admin.setUsername(adminUsername);
        admin.setRoles(Set.of(adminRole));

        PostEntity post = new PostEntity();
        long testId = 1L;
        post.setId(testId);
        post.setAuthorUsername(userUsername);

        when(userRepository.findByUsername(adminUsername)).thenReturn(Optional.of(admin));
        when(postRepository.findById(testId)).thenReturn(Optional.of(post));

        underTest.deletePost(testId, adminUsername);

        verify(postRepository).delete(post);
    }

   // @Test
    void deletePostFailedAndThrowNotAuthorizedExceptionWhenNotEnoughPermissions() {
        String username = "Test username";
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setRoles(Set.of(new RoleEntity(RoleEnum.USER)));

        String differentAuthorUsername = "I`m different";
        PostEntity post = new PostEntity();
        post.setId(1L);
        post.setAuthorUsername(differentAuthorUsername);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        assertThrows(NotAuthorizedException.class, () -> underTest.deletePost(1L, username),
                "You are not authorized to delete this post.");
    }

   // @Test
    void isOwnerTrue() {
        String ownerUsername = "Haho";
        PostEntity post = new PostEntity();
        post.setAuthorUsername(ownerUsername).setId(1L);

        UserEntity author = new UserEntity();
        author.setUsername(ownerUsername);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        assertTrue(underTest.isOwner(1L, ownerUsername));
    }

  //  @Test
    void isOwnerFalse() {
        String ownerUsername = "owner";
        PostEntity post = new PostEntity();
        post.setAuthorUsername(ownerUsername).setId(1L);

        UserEntity author = new UserEntity();
        author.setUsername("Paulo Coelho");

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        assertFalse(underTest.isOwner(1L, "Paulo Coelho"));
    }

   // @Test
    void isOwnerThrowsResourceNotFoundExceptionWhenPostDoesNotExists(){
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> underTest.isOwner(1L, "Tester"));
    }

   // @Test
    void isAdminFalse() {
        UserEntity user = new UserEntity();
        user.setUsername("Test username");
        user.setRoles(Set.of(new RoleEntity(RoleEnum.USER)));

        assertFalse(underTest.isAdmin(user));
    }

  //  @Test
    void isAdminTrue() {
        UserEntity user = new UserEntity();
        user.setUsername("Test username");
        user.setRoles(Set.of(new RoleEntity(RoleEnum.ADMIN)));

        assertTrue(underTest.isAdmin(user));
    }
}