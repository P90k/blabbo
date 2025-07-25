package com.blabbo.app.blabbo.repository;

import com.blabbo.app.blabbo.model.User;
import com.blabbo.app.blabbo.model.UserSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    Boolean existsByEmail(String email);

    List<UserSummary> findAllProjectedBy();
}

