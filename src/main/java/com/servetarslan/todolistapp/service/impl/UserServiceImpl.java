package com.servetarslan.todolistapp.service.impl;

import com.servetarslan.todolistapp.dto.UserCreateDto;
import com.servetarslan.todolistapp.dto.UserDto;
import com.servetarslan.todolistapp.exception.ResourceNotFoundException;
import com.servetarslan.todolistapp.model.User;
import com.servetarslan.todolistapp.repository.UserRepository;
import com.servetarslan.todolistapp.service.RoleService;
import com.servetarslan.todolistapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtoList = new ArrayList<>();
        Iterable<User> userIterable = userRepository.findAll();
        for (User user : userIterable) {
            UserDto userDto = EntityToDto(user);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    @Override
    public UserCreateDto createUser(UserCreateDto userCreateDto) {
        User user = DtoToEntity(userCreateDto);
        user.setRole(roleService.getUserRole());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User response = userRepository.save(user);
        return EntityToUserCreateDto(response);
    }

    @Override
    public UserDto getUserDtoById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User " + userId + " does not found!"));
        return EntityToDto(user);
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        User user = getUserById(userId);
        User userEntity = DtoToEntity(userDto);

        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setUsername(userEntity.getUsername());
        user.setMail(userEntity.getMail());
        user.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        user.setRole(roleService.findOrCreate(userEntity.getRole().getName()));

        User userUpdate = userRepository.save(user);
        return EntityToDto(userUpdate);
    }

    @Override
    public Map<String, Boolean> deleteUser(Long userId) {
        User user = getUserById(userId);
        user.setRole(null);
        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    protected User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User does not found!"));
    }

    private UserDto EntityToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private UserCreateDto EntityToUserCreateDto(User user) {
        return modelMapper.map(user, UserCreateDto.class);
    }

    private User DtoToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    private User DtoToEntity(UserCreateDto userCreateDto) {
        return modelMapper.map(userCreateDto, User.class);
    }
}
