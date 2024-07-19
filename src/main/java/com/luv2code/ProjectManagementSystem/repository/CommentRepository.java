package com.luv2code.ProjectManagementSystem.repository;

import com.luv2code.ProjectManagementSystem.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByIssuesId(Long issueId);
}
