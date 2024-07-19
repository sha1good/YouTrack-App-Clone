package com.luv2code.ProjectManagementSystem.service;

import com.luv2code.ProjectManagementSystem.model.Comments;
import com.luv2code.ProjectManagementSystem.model.Issues;
import com.luv2code.ProjectManagementSystem.model.User;
import com.luv2code.ProjectManagementSystem.repository.CommentRepository;
import com.luv2code.ProjectManagementSystem.repository.IssueRepository;
import com.luv2code.ProjectManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comments createComment(Long issueId, Long userId, String comment) throws Exception {
        Optional<Issues> issueOptional = issueRepository.findById(issueId);
        Optional<User> userOptional = userRepository.findUserById(userId);

        if (issueOptional.isEmpty()) {
            throw new Exception("Issue not found " + issueId);
        }

        if (userOptional.isEmpty()) {
            throw new Exception("User not found " + userId);
        }

        Issues issue = issueOptional.get();
        User user = userOptional.get();

        Comments comments = new Comments();
        comments.setIssues(issue);
        comments.setUser(user);
        comments.setCreatedDateTime(LocalDateTime.now());
        comments.setContent(comment);

        Comments savedComment = commentRepository.save(comments);
        issue.getComments().add(savedComment);
        return savedComment;
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {
        Optional<Comments> commentOptional = commentRepository.findById(commentId);
        Optional<User> userOptional = userRepository.findUserById(userId);


        if (commentOptional.isEmpty()) {
            throw new Exception("Issue not found " + commentId);
        }

        if (userOptional.isEmpty()) {
            throw new Exception("User not found " + userId);
        }
        Comments comments = commentOptional.get();
        User user = userOptional.get();

        if(comments.getUser().equals(user)) {
            commentRepository.deleteById(commentId);
        }
  else{
        throw new Exception("User does not have the permission to delete this comment");
        }
    }

    @Override
    public List<Comments> findCommentByIssueId(Long issueId) {
        return commentRepository.findByIssuesId(issueId);
    }
}
