package org.example.coding_convention.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.coding_convention.websocket.chat.ChatMessageDto;
import org.example.coding_convention.websocket.editor.EditorDto;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/{roomId}")
    public void chatMessage(Principal principal, @DestinationVariable String roomId, ChatMessageDto dto) {
        dto.setUsernameTime(principal.getName());
        messagingTemplate.convertAndSend("/topic/chat/" + roomId, dto);
    }

    @MessageMapping("/editor/{roomId}")
    public void editorMessage(@DestinationVariable String roomId, EditorDto.Code message) {
        messagingTemplate.convertAndSend("/topic/editor/" + roomId, message);
    }

}
