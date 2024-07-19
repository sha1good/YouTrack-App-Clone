package com.luv2code.ProjectManagementSystem.controller;


import com.luv2code.ProjectManagementSystem.model.Subscription;
import com.luv2code.ProjectManagementSystem.model.SubscriptionType;
import com.luv2code.ProjectManagementSystem.model.User;
import com.luv2code.ProjectManagementSystem.service.SubscriptionService;
import com.luv2code.ProjectManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/user")
    public ResponseEntity<Subscription> getUserSubscription(
             @RequestHeader("Authorization") String token
    ) throws Exception {
     User user = userService.findUserByToken(token);
     Subscription subscription = subscriptionService.getUserSubscription(user.getId());
      return new ResponseEntity<>(subscription, HttpStatus.OK);
    }


    @PatchMapping("/upgrade")
    public ResponseEntity<Subscription> upgradeSubScription( @RequestHeader("Authorization") String token,
                                                             @RequestParam SubscriptionType subscriptionType) throws Exception {
        User user = userService.findUserByToken(token);
        Subscription subscription = subscriptionService.upgradeUserSubscription(user.getId(), subscriptionType);
        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }
}
