package org.example.coding_convention.chat.repository;

import org.example.coding_convention.chat.model.Chats;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatsRepository extends JpaRepository<Chats, Integer> {

}
