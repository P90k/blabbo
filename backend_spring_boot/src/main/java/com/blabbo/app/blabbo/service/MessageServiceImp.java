package com.blabbo.app.blabbo.service;

import com.blabbo.app.blabbo.DTO.MessageDTO;
import com.blabbo.app.blabbo.exceptions.ResourceNotFound;
import com.blabbo.app.blabbo.model.Message;
import com.blabbo.app.blabbo.model.User;
import com.blabbo.app.blabbo.repository.MessageRepository;
import com.blabbo.app.blabbo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageServiceImp implements MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;


    public MessageServiceImp(MessageRepository msgRepo,
                             UserRepository userRepository) {
        this.messageRepository = msgRepo;
        this.userRepository    = userRepository;
    }


    @Override
    public void sendMessage(Message message) {
        messageRepository.save(message);
    }


    @Override
    public MessageDTO getMessage(Long id) {
        final Optional<Message> retrievedMsg = messageRepository.findById(id);
        if (retrievedMsg.isEmpty()) {
            throw new ResourceNotFound();
        }
        Message message = retrievedMsg.get();
        return new MessageDTO(message.getSender().getEmail(),
                              message.getRecipient().getEmail(),
                              message.getContent());
    }


    @Override
    public void deleteMessage(Long id) {
        if (!messageRepository.existsById(id)) {
            throw new ResourceNotFound();
        }
        messageRepository.deleteById(id);
    }


    @Override
    public Message createMessage(String content, String sender,
                                 String recipient) {
        User getSender = userRepository.findByEmail(sender);
        User getRecipient = userRepository.findByEmail(recipient);
        return new Message(getSender, getRecipient, content);
    }
}
