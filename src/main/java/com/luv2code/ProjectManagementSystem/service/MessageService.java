package com.luv2code.ProjectManagementSystem.service;

import com.luv2code.ProjectManagementSystem.model.Message;

import java.util.List;

public interface MessageService {
     Message sendMessage(Long sourceId, Long chatId, String content) throws Exception;

     List<Message> getMessageByProjectId(Long projectId) throws Exception;
}
