package com.society.server.dto.user;

import com.society.server.dto.photo.PhotoDTO;
import com.society.server.dto.post.PostDTO;
import com.society.server.model.entity.user.UserPersonalInfo;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private UserPersonalInfo userPersonalInfo;
    private List<PostDTO> userPosts;
    private List<PhotoDTO> userPhotos;
}
