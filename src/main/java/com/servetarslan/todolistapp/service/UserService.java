package com.servetarslan.todolistapp.service;

import com.servetarslan.todolistapp.dto.UserCreateOrUpdateDto;
import com.servetarslan.todolistapp.dto.UserDto;
import com.servetarslan.todolistapp.exception.ResourceNotFoundException;
import com.servetarslan.todolistapp.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    // CRUD
    List<UserDto> getAllUsers();
    UserCreateOrUpdateDto createUser(UserCreateOrUpdateDto userCreateDto);
    ResponseEntity<UserDto> getUserById(Long id) throws ResourceNotFoundException;
    ResponseEntity<UserCreateOrUpdateDto> updateUser(Long id, UserCreateOrUpdateDto userCreateDto);
    ResponseEntity<Map<String,Boolean>> deleteUser(Long id);

    // Model Mapper
    UserDto EntityToDto(User user);
    User DtoToEntity(UserDto userDto);
}
