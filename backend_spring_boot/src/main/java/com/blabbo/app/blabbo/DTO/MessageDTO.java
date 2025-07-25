package com.blabbo.app.blabbo.DTO;

public class MessageDTO {

    private final String senderEmail;

    private final String recipientEmail;

    private final String content;


    public MessageDTO(String senderEmail, String recipientEmail,
                      String content) {
        this.senderEmail    = senderEmail;
        this.recipientEmail = recipientEmail;
        this.content        = content;
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
}
