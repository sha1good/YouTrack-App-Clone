package com.luv2code.ProjectManagementSystem.repository;

import com.luv2code.ProjectManagementSystem.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
