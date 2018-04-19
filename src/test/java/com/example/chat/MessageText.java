package com.example.chat;

import com.example.chat.model.Message;
import com.example.chat.model.User;
import com.example.chat.repository.MessageRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author liuyiqian
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ChatApplicationTests.class)
public class MessageText {

    @Autowired
    private MessageRepository repository;

    @Test
    public void findTest() {
        Message message = repository.findById(1L).get();
        Assert.assertEquals(new Long(1), message.getFromUser().getId());
    }

    @Test
    public void addTest() {
        User user = new User("c", "12345");
        user.setId(1L);
        Message message = new Message("", "as.jpg", new Date(),user, null);
        repository.save(message);
    }
}
