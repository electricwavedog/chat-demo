package com.example.chat.controller;

import com.example.chat.model.User;
import com.example.chat.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author liuyiqian
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("add")
    public String addUser(User user) {
        userService.addUser(user);
        return "/login";
    }
}
