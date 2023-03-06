package com.society.server.api.v1;

import com.society.server.dto.message.MessageDTO;
import com.society.server.dto.message.RoomDTO;
import com.society.server.security.UserPrincipal;
import com.society.server.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.society.server.config.AppConstants.API_BASE;
import static java.lang.String.format;

@RestController
@RequestMapping(path = API_BASE)
public class ChatController {
    private final MessageService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatController(MessageService chatService, SimpMessagingTemplate simpMessagingTemplate) {
        this.chatService = chatService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/chat/{roomId}/sendMessage")
    public ResponseEntity<MessageDTO> sendMessage(@DestinationVariable Long roomId,
                                                  @Valid @Payload MessageDTO messageDTO,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        simpMessagingTemplate.convertAndSend(format("/channel/%s", roomId), messageDTO);

        chatService.saveMessage(messageDTO, roomId);

        return ResponseEntity.ok(messageDTO);
    }

    @GetMapping("/app/chat")
    public ResponseEntity<List<RoomDTO>> getChatRooms(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        List<RoomDTO> roomsByUser = chatService.getRoomsByUser(userPrincipal);
        return ResponseEntity.ok(roomsByUser);
    }

    @PostMapping("/app/chat")
    public ResponseEntity<Long> createRoom(@Valid @RequestBody RoomDTO roomDTO,
                                           BindingResult bindingResult,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Long roomId = chatService.createRoom(roomDTO, userPrincipal);

        return new ResponseEntity<>(roomId, HttpStatus.CREATED);
    }

    @GetMapping("/app/chat/{roomId}")
    public ResponseEntity<RoomDTO> getRoom(@PathVariable Long roomId) {
        RoomDTO roomDTO = chatService.getRoomById(roomId);
        return ResponseEntity.ok(roomDTO);
    }

}
