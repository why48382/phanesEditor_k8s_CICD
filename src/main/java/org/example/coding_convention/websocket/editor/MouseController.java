package org.example.coding_convention.websocket.editor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MouseController {

    // "/app/mouse" 경로로 들어온 메시지 처리
    @MessageMapping("/mouse")
    @SendTo("/topic/mouse")
    public MousePosition sendMousePosition(MousePosition mousePosition) throws Exception {
        // 간단하게 수신한 마우스 위치 그대로 반환하여 브로드캐스트
        return new MousePosition(mousePosition.getId(), mousePosition.getX(), mousePosition.getY());
    }

    @MessageMapping("/cursor")
    @SendTo("/topic/cursor")
    public CursorPosition editorCursor(CursorPosition cursorPosition) throws Exception {
        return new CursorPosition(cursorPosition.getSenderId(), cursorPosition.getLine(), cursorPosition.getColumn());
    }
}
