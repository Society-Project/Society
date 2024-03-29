package com.society.server.api.v1;

import com.society.server.dto.friendRequest.FriendRequestDTO;
import com.society.server.dto.friendRequest.FriendResponseDTO;
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

        FriendRequestDTO friendRequestDTO =
                friendRequestService.sendFriendRequest(userPrincipal.getUsername(), receiverId);

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
    public ResponseEntity<ResponseDTO<List<FriendResponseDTO>>> getAllRequests(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        List<FriendResponseDTO> allReceivedRequests =
                friendRequestService.getAllReceivedRequests(userPrincipal.getUsername());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDTO.<List<FriendResponseDTO>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Successfully received all requests for you.")
                        .content(allReceivedRequests)
                        .build());
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<ResponseDTO<FriendResponseDTO>> getRequest(
            @PathVariable Long requestId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        FriendResponseDTO requestById =
                friendRequestService.getRequestById(requestId, userPrincipal.getUsername());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDTO.<FriendResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("Successfully get request by id.")
                        .content(requestById)
                        .build());
    }

    @PostMapping("/{requestId}/accept")
    public ResponseEntity<ResponseDTO<FriendRequestDTO>> acceptFriendRequest(
            @PathVariable Long requestId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        FriendRequestDTO friendRequestDTO =
                friendRequestService.acceptFriendRequest(requestId, userPrincipal.getUsername());

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(ResponseDTO.<FriendRequestDTO>builder()
                        .message("Accepted the friend request!")
                        .status(HttpStatus.ACCEPTED.value())
                        .content(friendRequestDTO)
                        .build());
    }

    @GetMapping("/friends/{userId}")
    public ResponseEntity<ResponseDTO<List<FriendResponseDTO>>> getAllFriends(
            @PathVariable Long userId) {

        List<FriendResponseDTO> allFriends =
                friendRequestService.getAllFriends(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDTO.<List<FriendResponseDTO>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Successfully received all friends for you.")
                        .content(allFriends)
                        .build());

    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<ResponseDTO<Void>> deleteRequest(
            @PathVariable Long requestId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        friendRequestService.deleteRequestById(requestId, userPrincipal.getUsername());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDTO.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Successfully delete a request")
                        .build());
    }

    @PutMapping("/{userId}/block")
    public ResponseEntity<ResponseDTO<Void>> blockUser(
            @PathVariable Long userId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        friendRequestService.blockUser(userId, userPrincipal.getUsername());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDTO.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Successfully blocked a user.")
                        .build());
    }

    @PutMapping("/{userId}/unblock")
    public ResponseEntity<ResponseDTO<Void>> unblockUser(
            @PathVariable Long userId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        friendRequestService.unblockUser(userId, userPrincipal.getUsername());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDTO.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Successfully unblocked a user.")
                        .build());
    }

}
