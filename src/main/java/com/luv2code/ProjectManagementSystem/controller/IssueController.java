package com.luv2code.ProjectManagementSystem.controller;


import com.luv2code.ProjectManagementSystem.DTO.IssueDTO;
import com.luv2code.ProjectManagementSystem.model.Issues;
import com.luv2code.ProjectManagementSystem.model.User;
import com.luv2code.ProjectManagementSystem.request.IssueRequest;
import com.luv2code.ProjectManagementSystem.response.MessageResponse;
import com.luv2code.ProjectManagementSystem.service.IssueService;
import com.luv2code.ProjectManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;

    @GetMapping("/{issueId}")
    public ResponseEntity<Issues> getIssueById(
            @PathVariable Long issueId) throws Exception {

        return ResponseEntity.ok(issueService.getIssueById(issueId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issues>> getIssuesByProjectId(
            @PathVariable Long projectId) throws Exception {
        return ResponseEntity.ok(issueService.getIssuesByProjectId(projectId));
    }

    @PostMapping
    public ResponseEntity<IssueDTO> createIssues(
            @RequestBody IssueRequest issueReq, @RequestHeader("Authorization") String token) throws Exception {
        User tokenUser = userService.findUserByToken(token);
        // User user = userService.findUserById(tokenUser.getId());


        Issues createdIssues = issueService.createIssues(issueReq, tokenUser);
        IssueDTO issueDto = new IssueDTO();
        issueDto.setDescription(createdIssues.getDescription());
        issueDto.setAssignee(createdIssues.getAssignee());
        issueDto.setDueDate(createdIssues.getDueDate());
        issueDto.setId(createdIssues.getId());
        issueDto.setPriority(createdIssues.getPriority());
        issueDto.setProject(createdIssues.getProjects());
        issueDto.setProjectId(createdIssues.getProjectId());
        issueDto.setTitle(createdIssues.getTitle());
        issueDto.setTags(createdIssues.getTags());
        issueDto.setStatus(createdIssues.getStatus());
        return ResponseEntity.ok(issueDto);
    }

//        else{
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }


    @DeleteMapping("/{issueId}")
    public ResponseEntity<MessageResponse> deleteIssueById(
            @PathVariable Long issueId, @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByToken(token);
        issueService.deleteIssues(issueId, user.getId());
        MessageResponse response = new MessageResponse();
        response.setMessage("Issues has been successfully deleted");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{issuesId}/assignee/{userId}")
    public ResponseEntity<Issues> addUserToIssue(@PathVariable Long issuesId, @PathVariable Long userId) throws Exception {
        Issues issue = issueService.addUserToIssue(issuesId, userId);
        return ResponseEntity.ok(issue);
    }

    @PutMapping("/{issuesId}/status/{status}")
    public ResponseEntity<Issues> upDateIssueStatus(@PathVariable Long issuesId, @PathVariable String status) throws Exception {
        Issues issue = issueService.updateStatus(issuesId, status);
        return ResponseEntity.ok(issue);
    }
}

