package com.luv2code.ProjectManagementSystem.service;

import com.luv2code.ProjectManagementSystem.config.JwtProvider;
import com.luv2code.ProjectManagementSystem.model.User;
import com.luv2code.ProjectManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceimpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Override
    public User findUserByToken(String token) throws Exception {
        String email = JwtProvider.getEmailFromToken(token);
        return findUserByEmail(email);
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User>  optionalUser = userRepository.findUserById(userId);
        if(optionalUser.isEmpty()){
              throw new Exception("User not found!");
        }
        return optionalUser.get();
    }

    @Override
    public User findUserByEmail(String email) throws Exception {

        User user = userRepository.findByEmail(email);
        if(user == null) {
             throw new Exception("User not found!");
        }
        return user;
    }

    @Override
    public User updateUserProjectSize(User user, int number) {
        user.setProjectSize(user.getProjectSize() + number);
        return  userRepository.save(user);
    }
}
