package com.blabbo.app.blabbo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincrement
    private Long id;

    @NotBlank
    @Size(min = 4, max = 15)
    private String name;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    private String password;

    @OneToMany(mappedBy = "sender")
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "recipient")
    private List<Message> receivedMessages;


    public User() {
    }


    public User(String name, String email, String password) {
        this.name     = name;
        this.email    = email;
        this.password = password;
    }


    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;

    }


    public List<Message> getSentMessages() {
        return sentMessages;
    }


    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=PROTECTED, sentMessagesCount=" + (sentMessages == null ? 0 : sentMessages.size()) + ", receivedMessagesCount=" + (receivedMessages == null ? 0 : receivedMessages.size()) + "]";
    }

}
