package com.servetarslan.todolistapp.service.impl;

import com.servetarslan.todolistapp.dto.RoleDto;
import com.servetarslan.todolistapp.dto.UserCreateDto;
import com.servetarslan.todolistapp.dto.UserDto;
import com.servetarslan.todolistapp.model.Role;
import com.servetarslan.todolistapp.model.User;
import com.servetarslan.todolistapp.repository.UserRepository;
import com.servetarslan.todolistapp.service.RoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    @Spy
    private ModelMapper modelMapper;
    @Mock
    private RoleService roleService;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void when_getAllUsers_called_should_return_userDtoList() {
        // given
        User user = new User("test","test","test","test","test", new Role());

        // when
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        // then
        List<UserDto> getAllUsers = userService.getAllUsers();

        assertNotNull(getAllUsers);
        assertEquals(user.getUsername(),getAllUsers.get(0).getUsername());
        verify(userRepository,times(1)).findAll();
    }

    @Test
    public void when_createUser_called_should_return_userCreateDto() {
        UserCreateDto userCreateDto = UserCreateDto
                .builder()
                .firstName("test")
                .lastName("test")
                .username("test")
                .mail("test")
                .password("test")
                .build();

        User user = new User("test","test","test","test","test", new Role());

        when(userRepository.save(user)).thenReturn(user);
        when(roleService.getUserRole()).thenReturn(new Role());
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn(user.getPassword());

        UserCreateDto result = userService.createUser(userCreateDto);

        assertEquals(userCreateDto.getFirstName(), result.getFirstName());
        verify(userRepository,times(1)).save(user);
        verify(roleService,times(1)).getUserRole();
        verify(bCryptPasswordEncoder,times(1)).encode(anyString());
    }

    @Test
    public void when_getUserById_called_should_return_ResponseEntity_UserDto() {
        Long userId = 1L;
        User user = new User("test","test","test","test","test", new Role());

        UserDto userDto = UserDto
                .builder()
                .firstName("test")
                .lastName("test")
                .username("test")
                .mail("test")
                .password("test")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<UserDto> getUserById = userService.getUserById(userId);

        assertNotNull(getUserById);
        assertEquals(user.getUsername(), Objects.requireNonNull(getUserById.getBody()).getUsername());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void when_getUserByIdReturnUser_called_should_return_User() {
        Long userId = 1L;
        User user = new User("test","test","test","test","test", new Role());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User getUserByIdReturnUser = userService.getUserByIdReturnUser(userId);

        assertEquals(user.getUsername(),getUserByIdReturnUser.getUsername());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void when_updateUser_called_should_return_UserDto() {
        Long userId = 1L;
        UserDto userDto = UserDto
                .builder()
                .firstName("update-test")
                .lastName("update-test")
                .username("update-test")
                .mail("update-test")
                .password("update-test")
                .role(new RoleDto())
                .build();

        User userEntity = new User("update-test","update-test","update-test","update-test","update-test", new Role());

        User user = new User("test","test","test","test","test", new Role());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.encode(userEntity.getPassword())).thenReturn(userEntity.getPassword());
        when(roleService.findOrCreate(userEntity.getRole().getName())).thenReturn(new Role());
        when(userRepository.save(user)).thenReturn(userEntity);

        ResponseEntity<UserDto> updateUser = userService.updateUser(userId, userDto);

        assertNotNull(updateUser);
        assertEquals(userEntity.getUsername(), Objects.requireNonNull(updateUser.getBody()).getUsername());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void when_deleteUser_called_should_return_ResponseEntity_Map_Boolean() {
        Long userId = 1L;
        User user = new User("test","test","test","test","test", new Role());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<Map<String, Boolean>> deleteUser = userService.deleteUser(userId);

        assertNotNull(deleteUser);
        assertEquals(deleteUser.getStatusCodeValue(), 200);
        verify(userRepository, times(1)).delete(user);
    }
}