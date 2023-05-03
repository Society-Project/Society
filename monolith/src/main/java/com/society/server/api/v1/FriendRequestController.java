package com.society.server.api.v1;

import com.society.server.dto.friendRequest.FriendRequestDTO;
import com.society.server.dto.response.ResponseDTO;
import com.society.server.exception.UserNotFoundException;
import com.society.server.security.UserPrincipal;
import com.society.server.service.FriendRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static com.society.server.config.AppConstants.API_BASE;

@RestController
@RequestMapping(path = API_BASE + "/connection-request")
public class FriendRequestController {
    private final FriendRequestService friendRequestService;

    public FriendRequestController(FriendRequestService friendRequestService) {
        this.friendRequestService = friendRequestService;
    }

    @PostMapping("/send/{id}")
    public ResponseEntity<ResponseDTO<FriendRequestDTO>> sendRequest(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        FriendRequestDTO friendRequestDTO;
        try {
            friendRequestDTO = friendRequestService.sendFriendRequest(userPrincipal.getUsername(), id);
        } catch (UserNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDTO
                            .<FriendRequestDTO>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(exception.getMessage())
                            .timestamp(LocalDateTime.now())
                            .build());
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDTO
                        .<FriendRequestDTO>builder()
                        .content(friendRequestDTO)
                        .status(HttpStatus.CREATED.value())
                        .message("Successfully created a friend request!")
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}
