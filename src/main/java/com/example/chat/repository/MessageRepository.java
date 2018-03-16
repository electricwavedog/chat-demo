package com.example.chat.repository;

import com.example.chat.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author liuyiqian
 */
public interface MessageRepository extends JpaRepository<Message, Long> {
}
