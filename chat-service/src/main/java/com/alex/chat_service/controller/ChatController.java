package com.alex.chat_service.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alex.chat_service.models.ChatMessage;
import com.alex.chat_service.models.ChatMessage.MessageType;

@Controller
@RequestMapping("/ws-chat")
public class ChatController {
    @Autowired
    private SimpMessageSendingOperations messageTemplate;
 
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage){
        chatMessage.setTimestamp(LocalDateTime.now());
        chatMessage.setType(MessageType.CHAT);
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor){
        if (headerAccessor.getSessionAttributes() != null) {
            headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        } else {
            throw new IllegalStateException("Session attributes are null when add user");
        }
        chatMessage.setTimestamp(LocalDateTime.now());
        chatMessage.setType(MessageType.JOIN);
        return chatMessage;
    }

    @MessageMapping("/chat.sendPrivateMessage")
    public void sendPrivateMessage(@Payload ChatMessage chatMessage){
        chatMessage.setTimestamp(LocalDateTime.now());
        chatMessage.setType(MessageType.CHAT);

        messageTemplate.convertAndSendToUser(
            chatMessage.getRecipient(), "/queue/messages", chatMessage
        );
    }
}
