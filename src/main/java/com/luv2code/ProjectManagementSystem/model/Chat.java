package com.luv2code.ProjectManagementSystem.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Project project;

    @JsonIgnore
    @OneToMany(mappedBy ="chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;

    @ManyToMany
    private List<User> user = new ArrayList<>();
}
