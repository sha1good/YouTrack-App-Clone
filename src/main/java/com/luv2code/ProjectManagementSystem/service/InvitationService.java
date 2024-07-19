package com.luv2code.ProjectManagementSystem.service;

import com.luv2code.ProjectManagementSystem.model.Invitation;

public interface InvitationService {

    public void sendInvitation(String email, Long projectId) throws Exception;

    public Invitation acceptIvitation(String token, Long userId) throws Exception;

    public String getTokenByUserEmail(String userEmail);

     void deleteToken(String token);
}
