package com.blabbo.app.blabbo.DTO;

import java.time.LocalDateTime;

public class MessageDTO {

    private final String senderEmail;

    private final String recipientEmail;

    private final String content;

    private final Long id;

    private final LocalDateTime createdAt;


    public MessageDTO(Long id, LocalDateTime createdAt, String content,
                      String senderEmail, String recipientEmail) {
        this.id             = id;
        this.senderEmail    = senderEmail;
        this.recipientEmail = recipientEmail;
        this.content        = content;
        this.createdAt      = createdAt;
    }


    public String getSenderEmail() {
        return senderEmail;
    }


    public String getRecipientEmail() {
        return recipientEmail;
    }


    public String getContent() {
        return content;
    }


    public Long getId() {
        return id;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
