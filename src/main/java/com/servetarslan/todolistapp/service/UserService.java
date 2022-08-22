package com.servetarslan.todolistapp.service;

import com.servetarslan.todolistapp.dto.UserCreateDto;
import com.servetarslan.todolistapp.dto.UserDto;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<UserDto> getAllUsers();
    UserCreateDto createUser(UserCreateDto userCreateDto);
    UserDto getUserDtoById(Long id);
    UserDto updateUser(Long id, UserDto userDto);
    Map<String,Boolean> deleteUser(Long id);
}
