package com.example.chat.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author liuyiqian
 */
@Entity
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private String imgPath;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;

    @OneToOne
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @OneToOne
    @JoinColumn(name = "to_user_id")
    private User ToUser;

    public Message() {
    }

    public Message(String message, String imgPath, Date sendTime, User fromUser, User toUser) {
        this.message = message;
        this.imgPath = imgPath;
        this.sendTime = sendTime;
        this.fromUser = fromUser;
        ToUser = toUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return ToUser;
    }

    public void setToUser(User toUser) {
        ToUser = toUser;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}
