package com.luv2code.ProjectManagementSystem.controller;


import com.luv2code.ProjectManagementSystem.model.Chat;
import com.luv2code.ProjectManagementSystem.model.Invitation;
import com.luv2code.ProjectManagementSystem.model.Project;
import com.luv2code.ProjectManagementSystem.model.User;
import com.luv2code.ProjectManagementSystem.request.InvitationRequest;
import com.luv2code.ProjectManagementSystem.response.MessageResponse;
import com.luv2code.ProjectManagementSystem.service.InvitationService;
import com.luv2code.ProjectManagementSystem.service.ProjectService;
import com.luv2code.ProjectManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private InvitationService invitationService;

    @GetMapping
    public ResponseEntity<List<Project>> getProjects(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tags,
            @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByToken(token);
        List<Project> projects = projectService.getProjectByTeam(user, category, tags);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByToken(token);
        Project project = projectService.getProjectById(projectId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(
            @RequestHeader("Authorization") String token, @RequestBody Project project) throws Exception {
        User user = userService.findUserByToken(token);
        Project createdProject = projectService.createProject(project, user);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long projectId, @RequestBody Project project,
            @RequestHeader("Authorization") String token
    ) throws Exception {
        User user = userService.findUserByToken(token);
        Project updatedProject = projectService.updateProject(project, projectId);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }


    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponse> deleteProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String token
    ) throws Exception {
        User user = userService.findUserByToken(token);
        projectService.deleteProject(projectId, user.getId());
        MessageResponse response = new MessageResponse("Project has been deleted Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProjects(
            @RequestParam(required = false) String keyword,
            @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByToken(token);
        List<Project> projects = projectService.searchProjects(keyword, user);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }


    @GetMapping("/{projectId}/chat")
    public ResponseEntity<Chat> getChatByProjectId(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByToken(token);
        Chat chat = projectService.getChatByprojectId(projectId);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @PostMapping("/invite")
    public ResponseEntity<MessageResponse> createInvitation(
            @RequestHeader("Authorization") String token,
            @RequestBody InvitationRequest request) throws Exception {
        User user = userService.findUserByToken(token);
       invitationService.sendInvitation(request.getEmail(), request.getProjectId());
       MessageResponse response = new MessageResponse("Invitation sent Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/accept_invitation")
    public ResponseEntity<Invitation> acceptInvitation(
            @RequestHeader("Authorization") String token,
            @RequestParam String invitetoken) throws Exception {
        User user = userService.findUserByToken(token);
      Invitation invitation =  invitationService.acceptIvitation(invitetoken, user.getId());
      projectService.addUserToProject(invitation.getProjectId(), user.getId());
        return new ResponseEntity<>(invitation, HttpStatus.ACCEPTED);
    }
}
