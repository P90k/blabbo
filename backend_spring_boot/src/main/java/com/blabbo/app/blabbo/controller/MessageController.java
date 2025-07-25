package com.blabbo.app.blabbo.controller;

import com.blabbo.app.blabbo.DTO.MessageDTO;
import com.blabbo.app.blabbo.model.Message;
import com.blabbo.app.blabbo.service.interfaces.MessageService;
import com.blabbo.app.blabbo.service.MessageServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/message")
public class MessageController {

    private final MessageService msgService;
    private final MessageService messageService;


    public MessageController(MessageServiceImp msgService,
                             MessageService messageService) {
        this.msgService     = msgService;
        this.messageService = messageService;
    }


    @PostMapping("/send")
    public ResponseEntity<Message> postMethodName(
            @RequestBody MessageDTO messageDTO) {

        Message newMessage = msgService.createMessage(messageDTO.getContent(),
                                                      messageDTO.getSenderEmail(),
                                                      messageDTO.getRecipientEmail());
        msgService.sendMessage(newMessage);
        return ResponseEntity.ok().body(newMessage);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO> getMessage(@PathVariable Long id) {
        MessageDTO retrievedMsg = msgService.getMessage(id);
        return ResponseEntity.ok(retrievedMsg);
    }

    @GetMapping("")
    public ResponseEntity<List<MessageDTO>> getAllMessages() {
        return ResponseEntity.ok(msgService.getMessages());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        msgService.deleteMessage(id);
        // 204 indicates successful deletion
        return ResponseEntity.status(HttpStatus.valueOf(204)).build();
    }
}

