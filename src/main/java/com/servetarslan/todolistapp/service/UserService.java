package com.servetarslan.todolistapp.service;

import com.servetarslan.todolistapp.dto.UserCreateDto;
import com.servetarslan.todolistapp.dto.UserDto;
import com.servetarslan.todolistapp.exception.ResourceNotFoundException;
import com.servetarslan.todolistapp.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    // CRUD
    List<UserDto> getAllUsers();
    UserCreateDto createUser(UserCreateDto userCreateDto);
    ResponseEntity<UserDto> getUserById(Long id) throws ResourceNotFoundException;
    User getUserByIdReturnUser(Long id);
    ResponseEntity<UserDto> updateUser(Long id, UserDto userDto);
    ResponseEntity<Map<String,Boolean>> deleteUser(Long id);

    // Model Mapper
    UserDto EntityToDto(User user);
    User DtoToEntity(UserDto userDto);
}
