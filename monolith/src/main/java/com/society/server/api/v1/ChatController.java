package com.society.server.api.v1;

import com.society.server.dto.message.MessageDTO;
import com.society.server.dto.message.RoomDTO;
import com.society.server.security.UserPrincipal;
import com.society.server.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatController(ChatService chatService,
                          SimpMessagingTemplate simpMessagingTemplate) {
        this.chatService = chatService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/{roomId}/sendMessage")
    public ResponseEntity<MessageDTO> sendMessage(@PathVariable Long roomId,
                                                  @Valid @RequestBody MessageDTO messageDTO,
                                                  BindingResult bindingResult) {

        if (chatService.getRoomById(roomId) == null || bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        simpMessagingTemplate.convertAndSend(format("/channel/%d", roomId), messageDTO);

        chatService.saveMessage(messageDTO, roomId);

        return ResponseEntity.ok(messageDTO);
    }

    @GetMapping
    public ResponseEntity<List<RoomDTO>> getChatRooms(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        List<RoomDTO> roomsByUser = chatService.getRoomsByUser(userPrincipal);
        return ResponseEntity.ok(roomsByUser);
    }

    @PostMapping
    public ResponseEntity<Long> createRoom(@Valid @RequestBody RoomDTO roomDTO,
                                           BindingResult bindingResult,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Long roomId = chatService.createRoom(roomDTO, userPrincipal);

        return new ResponseEntity<>(roomId, HttpStatus.CREATED);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDTO> getRoom(@PathVariable Long roomId) {
        RoomDTO roomDTO = chatService.getRoomById(roomId);
        return ResponseEntity.ok(roomDTO);
    }

}
