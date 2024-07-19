package com.luv2code.ProjectManagementSystem.repository;

import com.luv2code.ProjectManagementSystem.model.Issues;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issues, Long> {
    List<Issues> findByprojectId(Long projectId);
}
