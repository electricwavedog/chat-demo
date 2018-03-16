package com.example.chat.model.dto;

import java.io.Serializable;

/**
 * @author liuyiqian
 */
public class MessageDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;

    private String recipient;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
