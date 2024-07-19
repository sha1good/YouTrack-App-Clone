package com.luv2code.ProjectManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String content;

    private LocalDateTime createdDateTime;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    private Issues issues;
}
