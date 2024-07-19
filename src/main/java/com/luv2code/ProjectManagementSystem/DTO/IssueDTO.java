package com.luv2code.ProjectManagementSystem.DTO;


import com.luv2code.ProjectManagementSystem.model.Project;
import com.luv2code.ProjectManagementSystem.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueDTO {
    private Long Id;
    private String title;
    private String description;
    private String status;
    private Long projectId;
    private String priority;
    private LocalDate dueDate;

    private List<String> tags = new ArrayList<>();

    private User assignee;

    private Project project;
}
