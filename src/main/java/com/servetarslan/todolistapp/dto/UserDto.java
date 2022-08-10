package com.servetarslan.todolistapp.dto;

import com.servetarslan.todolistapp.model.Role;
import com.servetarslan.todolistapp.model.ToDoList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Log4j2
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String mail;
    private String password;
    private RoleDto roleDto;
    private Set<ToDoListDto> toDoListDto;
}
