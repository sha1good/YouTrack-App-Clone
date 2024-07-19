package com.luv2code.ProjectManagementSystem.service;


import com.luv2code.ProjectManagementSystem.model.Chat;
import com.luv2code.ProjectManagementSystem.model.Message;
import com.luv2code.ProjectManagementSystem.model.User;
import com.luv2code.ProjectManagementSystem.repository.MessageRepository;
import com.luv2code.ProjectManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectService projectService;

    @Override
    public Message sendMessage(Long sourceId, Long chatId, String content) throws Exception {
        User sender = userRepository.findUserById(sourceId).orElseThrow(() -> new Exception("User not found!" + sourceId));
        Chat chat = projectService.getProjectById(chatId).getChat();
        Message message = new Message();
        message.setContent(content);
        message.setSender(sender);
        message.setChat(chat);
        message.setCreatedAt(LocalDateTime.now());
        Message savedMessage = messageRepository.save(message);
        chat.getMessages().add(savedMessage);
        return savedMessage;
    }

    @Override
    public List<Message> getMessageByProjectId(Long projectId) throws Exception {
        Chat chat = projectService.getChatByprojectId(projectId);
        List<Message> findByChatIdOrderByCreatedAtASC = messageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
        return findByChatIdOrderByCreatedAtASC;
    }
}
