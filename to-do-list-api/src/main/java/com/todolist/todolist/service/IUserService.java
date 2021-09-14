package com.todolist.todolist.service;

import com.todolist.todolist.dto.UserDto;
import com.todolist.todolist.model.User;

import java.util.List;

public interface IUserService {

    User findUserById(int id);

    User userDelete(int id);

    User userUpdate(int id, UserDto userDto);

    User saveUser(UserDto userDto);

    List<User> findAll();

}
