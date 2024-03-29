package com.pyikhine.todolist.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter @Setter @ToString @Builder(toBuilder = true) @With
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Todo title cannot be blank")
    @NotNull(message = "Todo title cannot be null")
    @Column(length = 100)
    @Size(min = 1, max = 100, message = "Todo title must be between 1 to 100 characters")
    private String todoTitle;

    private String status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "todo", cascade = CascadeType.ALL)
    @JsonIgnore
    @Builder.Default
    private List<Task> tasks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    private String username;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return id.equals(todo.id) &&
                todoTitle.equals(todo.todoTitle) &&
                status.equals(todo.status) &&
                username.equals(todo.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, todoTitle, status, username);
    }
}
