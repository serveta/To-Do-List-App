package com.servetarslan.todolistapp.service.impl;

import com.servetarslan.todolistapp.dto.UserCreateOrUpdateDto;
import com.servetarslan.todolistapp.dto.UserDto;
import com.servetarslan.todolistapp.exception.ResourceNotFoundException;
import com.servetarslan.todolistapp.model.User;
import com.servetarslan.todolistapp.repository.UserRepository;
import com.servetarslan.todolistapp.service.RoleService;
import com.servetarslan.todolistapp.service.UserService;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
    public UserCreateOrUpdateDto createUser(@RequestBody UserCreateOrUpdateDto userCreateDto) {
        User user = DtoToEntity(userCreateDto);
        user.setRole(roleService.getBasicRole());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return userCreateDto;
    }

    @SneakyThrows
    @Override
    public ResponseEntity<UserDto> getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User " + id + " does not found!"));
        UserDto userDto = EntityToDto(user);
        return ResponseEntity.ok(userDto);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<UserCreateOrUpdateDto> updateUser(Long id, UserCreateOrUpdateDto userCreateDto) {
        User userEntity = DtoToEntity(userCreateDto);

        User user = userRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User " + id + " does not found!"));

        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setUsername(userEntity.getUsername());
        user.setMail(userEntity.getMail());
        user.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));

        User userUpdate = userRepository.save(user);
        UserCreateOrUpdateDto responseUserCreateDto = EntityToUserCreateDto(userUpdate);
        return ResponseEntity.ok(responseUserCreateDto);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<Map<String, Boolean>> deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User " + id + " does not found!"));
        user.setRole(null);
        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    /////////// Model Mapper
    @Override
    public UserDto EntityToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public UserCreateOrUpdateDto EntityToUserCreateDto(User user) {
        return modelMapper.map(user, UserCreateOrUpdateDto.class);
    }

    @Override
    public User DtoToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public User DtoToEntity(UserCreateOrUpdateDto userCreateDto) {
        return modelMapper.map(userCreateDto, User.class);
    }
}
