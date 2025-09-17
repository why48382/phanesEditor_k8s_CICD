
package org.example.coding_convention.config;

import lombok.RequiredArgsConstructor;
import org.example.coding_convention.config.interceptor.AuthChannelInterceptor;
import org.example.coding_convention.config.interceptor.JwtHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final JwtHandshakeInterceptor jwtHandshakeInterceptor;
    private final AuthChannelInterceptor authChannelInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket")
                .addInterceptors(jwtHandshakeInterceptor)
                .setAllowedOrigins("https://www.gomorebi.kro.kr","http://localhost:5173","http://localhost:5175", "http://192.0.190.11:5173");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(authChannelInterceptor);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // 구독자가 메시지를 받을 경로
        registry.setApplicationDestinationPrefixes("/app"); // 서버가 메시지를 받을 경로
    }
}
