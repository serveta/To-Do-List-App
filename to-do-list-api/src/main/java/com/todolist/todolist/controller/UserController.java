package com.todolist.todolist.controller;

import com.todolist.todolist.dto.UserDto;
import com.todolist.todolist.model.User;
import com.todolist.todolist.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    User one(@PathVariable int id) {
        return userService.findUserById(id);
    }

    @DeleteMapping("/{id}")
    User userDelete(@PathVariable int id){
        return userService.userDelete(id);
    }

    @PutMapping("/{id}")
    User userUpdate(@PathVariable int id,
                    @RequestBody UserDto userDto){
        return userService.userUpdate(id, userDto);
    }

    @PostMapping
    User saveUser(@RequestBody UserDto userDto){
        return userService.saveUser(userDto);
    }

    @PostMapping("/email")
    User findUserByEmail(@RequestBody UserDto email) {
        return userService.findUserByEmail(email.getEmail());
    }

}
