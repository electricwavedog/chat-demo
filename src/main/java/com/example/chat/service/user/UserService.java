package com.example.chat.service.user;

import com.example.chat.model.User;

import java.util.List;

/**
 * @author liuyiqian
 */
public interface UserService {

    void addUser(User user);

    User findByUsername(String username);

    List<String> findAllUsername();
}
