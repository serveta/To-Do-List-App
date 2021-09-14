package com.todolist.todolist.service;

import com.todolist.todolist.dto.UserDto;
import com.todolist.todolist.exception.UserNotFoundException;
import com.todolist.todolist.model.User;
import com.todolist.todolist.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User findUserById(int id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User getUserId = findUserByEmail(authentication.getName());

        return userRepository.findById(getUserId.getId())
                .orElseThrow(() -> new UserNotFoundException("User could not find by id: " + id));

    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User userDelete(int id) {
        User user = findUserById(id);
        if(user != null) {
            userRepository.deleteById(user.getId());
        }
        return user;
    }

    public User userUpdate(int id, UserDto userDto) {
        User userUpdate = findUserById(id);
        if(userUpdate != null) {
            userUpdate.setName(userDto.getName());
            userUpdate.setSurname(userDto.getSurname());
            userUpdate.setEmail(userDto.getEmail());
            userUpdate.setPassword(userDto.getPassword());
            userRepository.save(userUpdate);
        }
        return userUpdate;
    }

    public User saveUser(UserDto userDto) {
        User newUser = new User();
        newUser.setName(userDto.getName());
        newUser.setSurname(userDto.getSurname());
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(userDto.getPassword());
        return userRepository.save(newUser);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
