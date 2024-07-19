package com.luv2code.ProjectManagementSystem.service;

import com.luv2code.ProjectManagementSystem.model.Chat;
import com.luv2code.ProjectManagementSystem.model.Project;
import com.luv2code.ProjectManagementSystem.model.User;

import java.util.List;

public interface ProjectService {

    Project createProject(Project project, User user) throws Exception;
    List<Project> getProjectByTeam(User user, String category, String tags) throws Exception;

    Project getProjectById(Long projctId) throws Exception;
     //void deleteProject(Long projectId) throws Exception;
     void deleteProject(Long projectId, Long userId) throws Exception;
    Project updateProject(Project updatedProject, Long id) throws Exception;
    void addUserToProject(Long projectId, Long userId) throws Exception;
    void  removeUserFromProject(Long projectId, Long userId) throws Exception;

    Chat getChatByprojectId(Long projectId) throws Exception;

    List<Project> searchProjects(String keyword, User user) throws Exception;
}
