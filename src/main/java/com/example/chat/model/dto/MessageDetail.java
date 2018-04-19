package com.example.chat.model.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @author liuyiqian
 */
public class MessageDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;

    private String recipient;

    private MultipartFile img;

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

    public MultipartFile getImg() {
        return img;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
    }
}
