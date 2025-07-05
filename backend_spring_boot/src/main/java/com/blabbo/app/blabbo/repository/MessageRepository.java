package com.blabbo.app.blabbo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.blabbo.app.blabbo.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{}
