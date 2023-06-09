package com.society.server.api.v1;

import com.society.server.dto.message.MessageDTO;
import com.society.server.dto.message.RoomDTO;
import com.society.server.dto.response.ResponseDTO;
import com.society.server.security.UserPrincipal;
import com.society.server.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.society.server.config.AppConstants.API_BASE;
import static java.lang.String.format;

@RestController
@RequestMapping(path = API_BASE + "/chat")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    //    /chat/app/{roomId}/sendMessage
    @MessageMapping("/{roomId}/sendMessage")
    public ResponseEntity<ResponseDTO<MessageDTO>> sendMessage(@PathVariable Long roomId,
                                                               @Valid @RequestBody MessageDTO messageDTO,
                                                               BindingResult bindingResult) {

        if (chatService.getRoomById(roomId) == null || bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDTO.<MessageDTO>builder()
                            .message("Wrong input data!")
                            .status(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

        MessageDTO message = chatService.saveMessage(messageDTO, roomId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDTO.<MessageDTO>builder()
                        .message("Successfully send a message!")
                        .status(HttpStatus.OK.value())
                        .content(message)
                        .build());
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<RoomDTO>>> getChatRooms(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        List<RoomDTO> roomsByUser = chatService.getRoomsByUser(userPrincipal);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDTO.<List<RoomDTO>>builder()
                        .message("Successfully get yours chat rooms!")
                        .status(HttpStatus.OK.value())
                        .content(roomsByUser)
                        .build());
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<RoomDTO>> createRoom(@Valid @RequestBody RoomDTO roomDTO,
                                                           BindingResult bindingResult,
                                                           @AuthenticationPrincipal UserPrincipal userPrincipal) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDTO.<RoomDTO>builder()
                            .message("Wrong input data!")
                            .status(HttpStatus.BAD_REQUEST.value())
                            .build());
        }
        RoomDTO room = chatService.createRoom(roomDTO, userPrincipal);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDTO.<RoomDTO>builder()
                        .message("Successfully created chat room!")
                        .status(HttpStatus.CREATED.value())
                        .content(room)
                        .build());
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<ResponseDTO<RoomDTO>> getRoom(@PathVariable Long roomId) {
        RoomDTO roomDTO = chatService.getRoomById(roomId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDTO.<RoomDTO>builder()
                        .message(format("Successfully get chat room with id: %d!", roomId))
                        .status(HttpStatus.OK.value())
                        .content(roomDTO)
                        .build());
    }

}
