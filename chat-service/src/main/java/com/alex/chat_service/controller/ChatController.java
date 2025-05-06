package com.alex.chat_service.controller;

import java.time.LocalDateTime;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.alex.chat_service.models.ChatMessage;

@Controller
public class ChatController {
 
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage){
        chatMessage.setTimestamp(LocalDateTime.now());
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor){
        if (headerAccessor.getSessionAttributes() != null) {
            headerAccessor.getSessionAttributes().put("sender", chatMessage.getSender());
        } else {
            throw new IllegalStateException("Session attributes are null");
        }
        chatMessage.setTimestamp(LocalDateTime.now());
        return chatMessage;
    }
}
