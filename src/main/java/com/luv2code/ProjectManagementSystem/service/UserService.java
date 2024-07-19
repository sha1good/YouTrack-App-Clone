package com.luv2code.ProjectManagementSystem.service;


import com.luv2code.ProjectManagementSystem.model.User;
import org.springframework.stereotype.Service;

public interface UserService {

   User findUserByToken(String token) throws Exception;
   User findUserById(Long userId) throws Exception;
   User findUserByEmail(String email) throws Exception;
   User  updateUserProjectSize(User user, int number);
}
