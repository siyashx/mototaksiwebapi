package com.codesupreme.mototaksiwebapi.dao.chat;

import com.codesupreme.mototaksiwebapi.model.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
