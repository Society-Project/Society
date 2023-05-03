package com.society.server.api.v1;

import com.society.server.dto.friendRequest.FriendRequestDTO;
import com.society.server.dto.response.ResponseDTO;
import com.society.server.security.UserPrincipal;
import com.society.server.service.FriendRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.society.server.config.AppConstants.API_BASE;

@RestController
@RequestMapping(path = API_BASE + "/friendship-requests")
public class FriendRequestController {
    private final FriendRequestService friendRequestService;

    public FriendRequestController(FriendRequestService friendRequestService) {
        this.friendRequestService = friendRequestService;
    }

    @PostMapping("/send/{receiverId}")
    public ResponseEntity<ResponseDTO<FriendRequestDTO>> sendRequest(
            @PathVariable Long receiverId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        FriendRequestDTO friendRequestDTO;
        try {
            friendRequestDTO = friendRequestService.sendFriendRequest(userPrincipal.getUsername(), receiverId);
        } catch (RuntimeException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDTO
                            .<FriendRequestDTO>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(exception.getMessage())
                            .build());
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDTO
                        .<FriendRequestDTO>builder()
                        .content(friendRequestDTO)
                        .status(HttpStatus.CREATED.value())
                        .message("Successfully created a friend request!")
                        .build());
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<FriendRequestDTO>>> getAllRequests(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {

        List<FriendRequestDTO> allReceivedRequests;
        try {
            allReceivedRequests = friendRequestService.getAllReceivedRequests(userPrincipal.getUsername());
        } catch (RuntimeException exception) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ResponseDTO.<List<FriendRequestDTO>>builder()
                            .status(HttpStatus.NOT_FOUND.value())
                            .message(exception.getMessage())
                            .build());
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDTO.<List<FriendRequestDTO>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Successfully received all requests for you.")
                        .content(allReceivedRequests)
                        .build());
    }

    @PostMapping("/{requestId}/accept")
    public ResponseEntity<ResponseDTO<FriendRequestDTO>> acceptFriendRequest(
            @PathVariable Long requestId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        FriendRequestDTO friendRequestDTO;
        try {
            friendRequestDTO =
                    friendRequestService.acceptFriendRequest(requestId, userPrincipal.getUsername());
        } catch (Exception exception) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ResponseDTO.<FriendRequestDTO>builder()
                            .message(exception.getMessage())
                            .status(HttpStatus.UNAUTHORIZED.value())
                            .build());
        }
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(ResponseDTO.<FriendRequestDTO>builder()
                        .message("Accepted the friend request!")
                        .status(HttpStatus.ACCEPTED.value())
                        .content(friendRequestDTO)
                        .build());
    }
}
