package com.luv2code.ProjectManagementSystem.controller;


import com.luv2code.ProjectManagementSystem.model.User;
import com.luv2code.ProjectManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

     @Autowired
    private UserService userService;

     @GetMapping("/profiles")
    public ResponseEntity<User> getUsersProfile(@RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByToken(token);
         return new ResponseEntity<>(user, HttpStatus.OK);
     }
}
