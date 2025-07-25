package com.blabbo.app.blabbo.model;

import com.blabbo.app.blabbo.DTO.MessageDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private User recipient;

    @NotBlank
    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;


    public Message() {
    }


    public Message(User sender, User recipient, @NotBlank String message) {
        this.sender    = sender;
        this.recipient = recipient;
        this.content   = message;
        this.createdAt = LocalDateTime.now();
    }


    public Long getId() {
        return id;
    }


    public User getSender() {
        return sender;
    }


    public void setSender(User sender) {
        this.sender = sender;
    }


    public User getRecipient() {
        return recipient;
    }


    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }


    public String getContent() {
        return content;
    }


    public void setContent(String message) {
        this.content = message;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public MessageDTO toDTO() {
        return new MessageDTO(this.id, this.createdAt, this.content,
                              this.sender.getEmail(),
                              this.recipient.getEmail());
    }


    @Override
    public String toString() {
        return "Message [id=" + id + ", senderId=" +
                (sender != null ? sender.getId() : null) + ", recipientId=" +
                (recipient != null ? recipient.getId() : null) + ", content=" +
                content + ", createdAt=" + createdAt + "]";
    }

}
