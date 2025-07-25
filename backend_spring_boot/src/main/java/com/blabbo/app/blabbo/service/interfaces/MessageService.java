package com.blabbo.app.blabbo.service.interfaces;

import com.blabbo.app.blabbo.DTO.MessageDTO;
import com.blabbo.app.blabbo.model.Message;

import java.util.List;

public interface MessageService {
    Message createMessage(String content, String sender, String recipient);


    MessageDTO getMessage(Long id);


    List<MessageDTO> getMessages();


    void deleteMessage(Long id);


    void sendMessage(Message message);
}
