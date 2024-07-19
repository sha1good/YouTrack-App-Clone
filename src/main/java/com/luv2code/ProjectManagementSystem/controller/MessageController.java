package com.luv2code.ProjectManagementSystem.controller;


import com.luv2code.ProjectManagementSystem.model.Chat;
import com.luv2code.ProjectManagementSystem.model.Message;
import com.luv2code.ProjectManagementSystem.model.User;
import com.luv2code.ProjectManagementSystem.request.CreateMessageRequest;
import com.luv2code.ProjectManagementSystem.service.MessageService;
import com.luv2code.ProjectManagementSystem.service.ProjectService;
import com.luv2code.ProjectManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody CreateMessageRequest createMessageReq) throws Exception {
        User user = userService.findUserById(createMessageReq.getSenderId());
        if (user == null) throw new Exception("User not found !" + createMessageReq.getSenderId());
        //Chat chat = projectService.getChatByprojectId(createMessageReq.getProjectId());
        Chat chat = projectService.getProjectById(createMessageReq.getProjectId()).getChat();
        if (chat == null) throw new Exception("User not found !" + createMessageReq.getProjectId());

        Message sentMessage = messageService.sendMessage(createMessageReq.getSenderId(), createMessageReq.getProjectId(), createMessageReq.getContent());
        return new ResponseEntity<>(sentMessage, HttpStatus.OK);
    }
    @GetMapping("/chat/{projectId}")
        public ResponseEntity<List<Message>> getMessageByChatId(@PathVariable Long projectId) throws Exception {
          List<Message> messages =  messageService.getMessageByProjectId(projectId);
          return ResponseEntity.ok(messages);
        }

}
