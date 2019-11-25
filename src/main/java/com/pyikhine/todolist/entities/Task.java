package com.pyikhine.todolist.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Getter @Setter @ToString
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150)
    @Size(min = 1, max = 150, message = "Task description must be between 1 to 150 characters")
    @NotBlank(message = "Task description cannot be blank")
    @NotNull(message = "Task description cannot be null")
    private String taskDescription;

    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "todo_id")
    @JsonIgnore
    private Todo todo;

    private Date createdAt;

    private Date updatedAt;

    @PrePersist
    protected void create() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void update() {
        updatedAt = new Date();
    }
}
