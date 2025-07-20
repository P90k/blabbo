package com.blabbo.app.blabbo.repository;

import com.blabbo.app.blabbo.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
