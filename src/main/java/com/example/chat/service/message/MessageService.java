package com.example.chat.service.message;

import com.example.chat.model.Message;
import com.example.chat.model.dto.MessageDetail;

/**
 * @author liuyiqian
 */
public interface MessageService {

    Message addMessage(MessageDetail messageDetail, String username);
}
