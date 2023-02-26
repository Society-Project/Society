package com.society.server.api.v1;

import com.society.server.dto.message.MessageDTO;
import com.society.server.dto.message.RoomDTO;
import com.society.server.model.entity.RoomEntity;
import com.society.server.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        chatService.saveMessage(messageDTO, roomId);

        simpMessagingTemplate.convertAndSend(format("/channel/%s", roomId), messageDTO);

        return ResponseEntity.ok(messageDTO);
    }

    @PostMapping("/app/chat")
    public ResponseEntity<RoomDTO> createRoom(@Valid RoomDTO roomDTO,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        chatService.createRoom(roomDTO);

        return ResponseEntity.ok(roomDTO);
    }
}
