package com.servetarslan.todolistapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends BaseEntity{
    @Column
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
    @JsonBackReference
    private Set<User> user;
}
