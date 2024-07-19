package com.luv2code.ProjectManagementSystem.repository;

import com.luv2code.ProjectManagementSystem.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long>{

    Invitation findByToken(String token);
    Invitation findByEmail(String email);
}
