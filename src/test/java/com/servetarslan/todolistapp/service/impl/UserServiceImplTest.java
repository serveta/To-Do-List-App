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
    public void when_getAllUsers_called_should_return_List_userDto() {
        User user = new User();
        user.setFirstName("test-f-name");
        user.setLastName("test-l-name");
        user.setUsername("test-username");
        user.setMail("test-mail@test.com");
        user.setPassword("test-pass");
        user.setRole(new Role());

        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        List<UserDto> getAllUsers = userService.getAllUsers();

        assertNotNull(getAllUsers);
        assertEquals(user.getUsername(),getAllUsers.get(0).getUsername());
        verify(userRepository).findAll();
    }

    @Test
    public void when_createUser_called_should_return_userCreateDto() {
        UserCreateDto userCreateDto = UserCreateDto
                .builder()
                .firstName("test-f-name")
                .lastName("test-l-name")
                .username("test-username")
                .mail("test-mail@test.com")
                .password("test-pass")
                .build();

        User user = new User();
        user.setFirstName("test-f-name");
        user.setLastName("test-l-name");
        user.setUsername("test-username");
        user.setMail("test-mail@test.com");
        user.setPassword("test-pass");
        user.setRole(new Role());

        when(roleService.getUserRole()).thenReturn(new Role());
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn(user.getPassword());
        when(userRepository.save(user)).thenReturn(user);

        UserCreateDto result = userService.createUser(userCreateDto);

        assertEquals(userCreateDto.getFirstName(), result.getFirstName());
        verify(userRepository).save(user);
        verify(roleService).getUserRole();
        verify(bCryptPasswordEncoder).encode(anyString());
    }

    @Test
    public void when_getUserDtoById_called_should_return_UserDto() {
        Long userId = 1L;
        User user = new User();
        user.setFirstName("test-f-name");
        user.setLastName("test-l-name");
        user.setUsername("test-username");
        user.setMail("test-mail@test.com");
        user.setPassword("test-pass");
        user.setRole(new Role());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDto response = userService.getUserDtoById(userId);

        assertEquals(user.getUsername(), response.getUsername());
        verify(userRepository).findById(userId);
    }

    @Test
    public void when_getUserById_called_should_return_User() {
        Long userId = 1L;
        User user = new User();
        user.setFirstName("test-f-name");
        user.setLastName("test-l-name");
        user.setUsername("test-username");
        user.setMail("test-mail@test.com");
        user.setPassword("test-pass");
        user.setRole(new Role());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User response = userService.getUserById(userId);

        assertEquals(user.getUsername(), response.getUsername());
        verify(userRepository).findById(userId);
    }

    @Test
    public void when_updateUser_called_should_return_UserDto() {
        Long userId = 1L;
        UserDto userDto = UserDto
                .builder()
                .firstName("update-firstname-test")
                .lastName("update-lastname-test")
                .username("update-username-test")
                .mail("update-mail@test.com")
                .password("update-pass-test")
                .role(new RoleDto())
                .build();

        User userUpdate = new User();
        userUpdate.setFirstName("update-firstname-tes");
        userUpdate.setLastName("update-lastname-test");
        userUpdate.setUsername("update-username-test");
        userUpdate.setMail("update-mail@test.com");
        userUpdate.setPassword("update-pass-test");
        userUpdate.setRole(new Role());

        User user = new User();
        user.setFirstName("test-f-name");
        user.setLastName("test-l-name");
        user.setUsername("test-username");
        user.setMail("test-mail@test.com");
        user.setPassword("test-pass");
        user.setRole(new Role());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.encode(userUpdate.getPassword())).thenReturn(userUpdate.getPassword());
        when(roleService.findOrCreate(userUpdate.getRole().getName())).thenReturn(new Role());
        when(userRepository.save(user)).thenReturn(userUpdate);

        UserDto response = userService.updateUser(userId, userDto);

        assertEquals(userUpdate.getUsername(), response.getUsername());
        verify(userRepository).findById(userId);
        verify(userRepository).save(user);
    }

    @Test
    public void when_deleteUser_called_should_return_Map() {
        Long userId = 1L;
        User user = new User();
        user.setFirstName("test-f-name");
        user.setLastName("test-l-name");
        user.setUsername("test-username");
        user.setMail("test-mail@test.com");
        user.setPassword("test-pass");
        user.setRole(new Role());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Map<String, Boolean> response = userService.deleteUser(userId);

        assertNotNull(response);
        assertEquals(Boolean.TRUE, response.get("deleted"));
        verify(userRepository).delete(user);
    }
}