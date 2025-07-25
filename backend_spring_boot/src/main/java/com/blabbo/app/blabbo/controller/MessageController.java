package com.blabbo.app.blabbo.controller;

import com.blabbo.app.blabbo.DTO.MessageDTO;
import com.blabbo.app.blabbo.model.Message;
import com.blabbo.app.blabbo.service.interfaces.MessageService;
import com.blabbo.app.blabbo.service.MessageServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/message")
public class MessageController {

    private final MessageService msgService;


    public MessageController(MessageServiceImp msgService) {
        this.msgService = msgService;
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


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        msgService.deleteMessage(id);
        // 204 indicates successful deletion
        return ResponseEntity.status(HttpStatus.valueOf(204)).build();
    }
}

