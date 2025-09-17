package org.example.coding_convention.chat.service;

import lombok.RequiredArgsConstructor;
import org.example.coding_convention.chat.model.ChatsDto;
import org.example.coding_convention.chat.repository.ChatsRepository;
import org.example.coding_convention.user.model.UserDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatsRepository chatsRepository;

    public void save(ChatsDto.ChatRegister dto, UserDto.AuthUser aUser) {
        chatsRepository.save(dto.toEntity(aUser));
    }
}
