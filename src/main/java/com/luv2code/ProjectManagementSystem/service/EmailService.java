package com.luv2code.ProjectManagementSystem.service;

public interface EmailService {
     void sendEmailWithToken(String userEmail, String link) throws Exception;
}
