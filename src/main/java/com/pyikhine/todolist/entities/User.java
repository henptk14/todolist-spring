package com.pyikhine.todolist.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder(toBuilder = true)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Full name cannot be blank")
    @Size(min = 2, max = 40, message = "Full name must be between 2 to 40 characters")
    private String fullName;

    @NotBlank(message = "Username cannot be blank")
    @Column(unique = true)
    @Size(min = 4, max = 15, message = "Username must be between 4 to 15 characters")
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Email
    @Column(unique = true)
    @Size(max = 40, message = "Email must be less be less than 40 characters")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 100, message = "Password must be between 8 to 100 characters")
    private String password;

    @Transient
    private String confirmPassword;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user", orphanRemoval = true)
    private List<Todo> todoList = new ArrayList<>();

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

    /* UserDetails interface methods */

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
