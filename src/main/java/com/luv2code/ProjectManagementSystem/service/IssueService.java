package com.luv2code.ProjectManagementSystem.service;

import com.luv2code.ProjectManagementSystem.model.Issues;
import com.luv2code.ProjectManagementSystem.model.User;
import com.luv2code.ProjectManagementSystem.request.IssueRequest;

import java.util.List;
import java.util.Optional;

public interface IssueService {
    Issues getIssueById(Long issueId) throws Exception;

    List<Issues> getIssuesByProjectId(Long projectId) throws Exception;
    Issues  createIssues(IssueRequest issuesReq, User user) throws Exception;
     void deleteIssues(Long issuesId, Long userId) throws Exception;
    Issues  addUserToIssue( Long issuesId, Long userId) throws Exception;
    Issues updateStatus(Long issueId, String status) throws Exception;
}
