package org.example.coding_convention.config.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Map;

// 웹 소켓 통신으로 클라이언트가 서버로 데이터를 보낼 때
@Component
public class AuthChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        // 만료 시간 확인 추가
        // 특정 경로마다 권한 확인
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            Map<String, Object> attributes = accessor.getSessionAttributes();
            if (attributes != null) {
                Authentication authentication = (Authentication) attributes.get("auth");
                if (authentication != null) {
                    accessor.setUser(authentication);
                }
            }
        }
        return message;
    }
}