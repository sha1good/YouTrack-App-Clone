package com.luv2code.ProjectManagementSystem.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
public class Issues {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;
    private String status;
    private Long projectId;
    private String priority;
    private LocalDate dueDate;

    @ElementCollection
    @CollectionTable(name = "issue_tags", joinColumns = @JoinColumn(name = "issue_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();


    @ManyToOne
    private User assignee;

    @JsonIgnore
    @ManyToOne
    private Project projects;

    @JsonIgnore
    @OneToMany(mappedBy = "issues", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comments> comments = new ArrayList<>();
}
