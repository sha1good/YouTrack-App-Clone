package com.luv2code.ProjectManagementSystem.service;


import com.luv2code.ProjectManagementSystem.model.Issues;
import com.luv2code.ProjectManagementSystem.model.Project;
import com.luv2code.ProjectManagementSystem.model.User;
import com.luv2code.ProjectManagementSystem.repository.IssueRepository;
import com.luv2code.ProjectManagementSystem.request.IssueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Override
    public Issues getIssueById(Long issueId) throws Exception {
        Optional<Issues> issues = issueRepository.findById(issueId);

        if(issues.isPresent()){
             return issues.get();
        }
         throw new Exception("Issues not found!" + issueId  );
    }

    @Override
    public List<Issues> getIssuesByProjectId(Long projectId) throws Exception {
        return issueRepository.findByprojectId(projectId);
    }

    @Override
    public Issues createIssues(IssueRequest issuesReq, User user) throws Exception {
        Project project = projectService.getProjectById(issuesReq.getProjectId());
        Issues issues = new Issues();
        issues.setTitle(issuesReq.getTitle());
        issues.setDescription(issuesReq.getDescription());
        issues.setProjectId(issuesReq.getProjectId());
        issues.setDueDate(issuesReq.getDueDate());
        issues.setStatus(issuesReq.getStatus());
        issues.setPriority(issuesReq.getPriority());

        issues.setProjects(project);
        return  issueRepository.save(issues);
    }


    @Override
    public  void deleteIssues(Long issuesId, Long userId) throws Exception {
        getIssueById(issuesId);
        issueRepository.deleteById(issuesId);
    }

    @Override
    public Issues addUserToIssue(Long issuesId, Long userId) throws Exception {
        User user = userService.findUserById(userId);
        Issues issues = getIssueById(issuesId);

        issues.setAssignee(user);
        return  issueRepository.save(issues);
    }

    @Override
    public Issues updateStatus(Long issueId, String status) throws Exception {
        Issues issue = getIssueById(issueId);
        issue.setStatus(status);
        return  issueRepository.save(issue);
    }
}
