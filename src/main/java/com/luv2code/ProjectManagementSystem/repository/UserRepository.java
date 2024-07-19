package com.luv2code.ProjectManagementSystem.repository;

import com.luv2code.ProjectManagementSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Id> {
    public User  findByEmail(String email);

    Optional<User> findUserById(Long userId);
}
