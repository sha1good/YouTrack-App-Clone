package com.luv2code.ProjectManagementSystem.service;

import com.luv2code.ProjectManagementSystem.model.Chat;
import com.luv2code.ProjectManagementSystem.model.Project;
import com.luv2code.ProjectManagementSystem.model.User;
import com.luv2code.ProjectManagementSystem.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Override
    public Project createProject(Project project, User user) throws Exception {
        Project createdproject = new Project();
        createdproject.setOwner(user);
        createdproject.setTags(project.getTags());
        createdproject.setName(project.getName());
        createdproject.setCategory(project.getCategory());
        createdproject.setDescription(project.getDescription());
        createdproject.getTeam().add(user);

        Object savedProject = projectRepository.save(createdproject);

        Chat chat = new Chat();
        chat.setProject((Project) savedProject);
        Chat projectChat = chatService.createChat(chat);
        ((Project) savedProject).setChat(projectChat);
        return (Project) savedProject;
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tags) throws Exception {

        List<Project> projects = projectRepository.findByTeamContainingOrOwner(user, user);
        if (category != null) {
            projects = projects.stream().filter(project -> project.getCategory().equals(category)).collect(Collectors.toList());
        }

        if (tags != null) {
            projects = projects.stream().filter(project -> project.getTags().contains(tags)).collect(Collectors.toList());
        }
        return projects;
    }

    @Override
    public Project getProjectById(Long projctId) throws Exception {
        Optional<Project> optionalProject = projectRepository.findById(projctId);
        if (optionalProject.isEmpty()) {
            throw new Exception("Project Not Found!");
        }
        return optionalProject.get();
    }

    @Override
    public void deleteProject(Long projectId, Long userId) throws Exception {
        userService.findUserById(userId);
        projectRepository.deleteById(projectId);
    }

    @Override
    public Project updateProject(Project updatedProject, Long id) throws Exception {
        Project newProject = getProjectById(id); // I called the getProjectById function that I declared
        newProject.setName(updatedProject.getName());
        newProject.setDescription(updatedProject.getDescription());
        newProject.setTags(updatedProject.getTags());
        return (Project) projectRepository.save(newProject);
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);
        for (User member : project.getTeam()) {
            if (member.getId().equals(user.getId())) {
                return;
            }

        }
        project.getTeam().add(user);
        project.getChat().getUser().add(user);
        projectRepository.save(project);
    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);

        if (project.getTeam().contains(user)) {
            project.getTeam().remove(user);
            project.getChat().getUser().remove(user);
        }
        projectRepository.delete(project);
    }

    @Override
    public Chat getChatByprojectId(Long projectId) throws Exception {
        Project project = getProjectById(projectId);
        return project.getChat();
    }

    @Override
    public List<Project> searchProjects(String keyword, User user) throws Exception {
        // String partialName = "%" + keyword + "%";
        return projectRepository.findByNameContainingAndTeamContaining(keyword, user);
    }


}
