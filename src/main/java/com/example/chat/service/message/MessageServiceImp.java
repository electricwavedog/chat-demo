package com.example.chat.service.message;

import com.example.chat.common.FileUtil;
import com.example.chat.config.WebAppConfig;
import com.example.chat.model.Message;
import com.example.chat.model.User;
import com.example.chat.model.dto.MessageDetail;
import com.example.chat.repository.MessageRepository;
import com.example.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * @author liuyiqian
 */
@Service
public class MessageServiceImp implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebAppConfig webAppConfig;

    @Override
    public Message addMessage(MessageDetail messageDetail, String username) {
        Message message = new Message();
        message.setMessage(messageDetail.getMessage());
        User fromUser = userRepository.findByUsername(username);
        if (fromUser != null) {
//            fromUser.setPassword(null);
            message.setFromUser(fromUser);
        }
        if (messageDetail.getRecipient() != null) {
            User toUser = userRepository.findByUsername(messageDetail.getRecipient());
            if (toUser != null) {
//                toUser.setPassword(null);
                message.setToUser(toUser);
            }
        }
        if (messageDetail.getImg() != null) {
            String fileName = Long.toString(System.currentTimeMillis()) + ".jpg";
            FileUtil.uploadFile(messageDetail.getImg(), webAppConfig.getLocation(), fileName);
            message.setImgPath(fileName);
        }
        message.setSendTime(new Date());
        messageRepository.save(message);
        return message;
    }
}
