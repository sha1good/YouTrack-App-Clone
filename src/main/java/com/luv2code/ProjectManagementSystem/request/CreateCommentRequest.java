package com.luv2code.ProjectManagementSystem.request;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequest {

    private Long issueId;

    private String comment;
}


