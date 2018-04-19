package com.example.chat.controller;

import com.example.chat.model.Message;
import com.example.chat.model.dto.MessageDetail;
import com.example.chat.service.message.MessageService;
import com.example.chat.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * @author liuyiqian
 */
@Controller
public class ChatController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/send")
    @SendTo("/topic/accept")
    public Message openChat(MessageDetail messageDetail, Principal principal) {
        Message message = messageService.addMessage(messageDetail, principal.getName());
        return message;
    }

    @MessageMapping("/notify")
    @SendTo("/topic/online")
    public String online(Principal principal) {
        return principal.getName();
    }

    @RequestMapping("/chat/{recipient}")
    public ModelAndView toPrivate(@PathVariable String recipient, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("recipient", recipient);
        modelAndView.addObject("usernameList", userService.findAllUsername());
        modelAndView.addObject("username", principal.getName());
        modelAndView.setViewName("chat");
        return modelAndView;
    }

    @MessageMapping("/private")
    public void privateChat(MessageDetail messageDetail, Principal principal) {
        Message message = messageService.addMessage(messageDetail, principal.getName());
        simpMessagingTemplate.convertAndSendToUser(messageDetail.getRecipient(), "/topic/private", message);
    }

    @RequestMapping("/chat")
    public ModelAndView toOpen(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("usernameList", userService.findAllUsername());
        modelAndView.addObject("username", principal.getName());
        modelAndView.setViewName("chat");
        return modelAndView;
    }

    @PostMapping("/image")
    public void upload(Principal principal, MessageDetail messageDetail) {
        Message message = messageService.addMessage(messageDetail, principal.getName());
        simpMessagingTemplate.convertAndSend("/topic/image", message);
    }
}
