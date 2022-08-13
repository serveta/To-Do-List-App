package com.servetarslan.todolistapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Column(name = "first_name")
    @NotBlank(message = "First name can't be empty!")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Last name can't be empty!")
    private String lastName;

    @Column(name = "username", unique = true)
    @NotBlank(message = "Username can't be empty!")
    private String username;

    @Column(name = "mail", unique = true)
    @NotBlank(message = "Mail can't be empty!")
    private String mail;

    @Column(name = "password")
    @NotBlank(message = "Password can't be empty!")
    private String password;

    @ManyToOne(cascade = CascadeType.ALL)
    private Role role;
}
