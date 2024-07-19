package com.luv2code.ProjectManagementSystem.controller;


import com.luv2code.ProjectManagementSystem.model.Comments;
import com.luv2code.ProjectManagementSystem.model.User;
import com.luv2code.ProjectManagementSystem.request.CreateCommentRequest;
import com.luv2code.ProjectManagementSystem.response.MessageResponse;
import com.luv2code.ProjectManagementSystem.service.CommentService;
import com.luv2code.ProjectManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;


    @PostMapping()
    public ResponseEntity<Comments> createComment(
            @RequestBody CreateCommentRequest commentReq, @RequestHeader("Authorization") String token
    ) throws Exception {
        User user = userService.findUserByToken(token);
        Comments createdComment = commentService.createComment(commentReq.getIssueId(), user.getId(), commentReq.getComment());

        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable Long commentId, @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByToken(token);
        commentService.deleteComment(commentId, user.getId());
        MessageResponse messageResponse = new MessageResponse("Comment has been deleted Successfully");
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);

    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comments>> getCommentByIssueId(@PathVariable Long issueId, @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByToken(token);
        List<Comments> comments = commentService.findCommentByIssueId(issueId);
        return new ResponseEntity<>(comments, HttpStatus.OK);

    }
}
