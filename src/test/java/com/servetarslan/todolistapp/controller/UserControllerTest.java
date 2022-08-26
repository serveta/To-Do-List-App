package com.servetarslan.todolistapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.servetarslan.todolistapp.dto.RoleDto;
import com.servetarslan.todolistapp.dto.UserCreateDto;
import com.servetarslan.todolistapp.dto.UserDto;
import com.servetarslan.todolistapp.security.JWTokenUtil;
import com.servetarslan.todolistapp.security.JwtAuthenticationEntryPoint;
import com.servetarslan.todolistapp.service.UserService;
import com.servetarslan.todolistapp.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @MockBean
    private UserService userService;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private JWTokenUtil jwTokenUtil;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    public void when_getAllUsers_called_should_return_status200() throws Exception {
        UserDto user1 = new UserDto();
        user1.setFirstName("test-f-name");
        user1.setLastName("test-l-name");
        user1.setUsername("test-username");
        user1.setMail("test-mail@test.com");
        user1.setPassword("test-pass");
        user1.setRole(new RoleDto());

        UserDto user2 = new UserDto();
        user2.setFirstName("test-2-f-name");
        user2.setLastName("test-2-l-name");
        user2.setUsername("test-2-username");
        user2.setMail("test-2-mail@test.com");
        user2.setPassword("test-2-pass");
        user2.setRole(new RoleDto());

        List<UserDto> listOfUserDto = new ArrayList<>();
        listOfUserDto.add(user1);
        listOfUserDto.add(user2);

        when(userService.getAllUsers()).thenReturn(listOfUserDto);

        mockMvc.perform(get("/api/v1/users")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username").value(listOfUserDto.get(0).getUsername()))
                .andExpect(jsonPath("$[1].mail").value(listOfUserDto.get(1).getMail()))
                .andDo(print());
    }

    @Test
    public void when_createUser_called_should_return_status200() throws Exception {
        UserCreateDto userCreateDto = UserCreateDto
                .builder()
                .firstName("test-firstname")
                .lastName("test-lastname")
                .username("test-username")
                .mail("test@mail.com")
                .password("test-pass")
                .build();

        when(userService.createUser(userCreateDto)).thenReturn(userCreateDto);

        mockMvc.perform(post("/api/v1/users/register")
                        .content(objectMapper.writeValueAsString(userCreateDto))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(userCreateDto.getUsername()))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    public void when_getUserDtoById_called_should_return_status200() throws Exception {
        Long userId = 1L;
        UserDto userDto = UserDto
                .builder()
                .id(userId)
                .firstName("test-firstname")
                .lastName("test-lastname")
                .username("test-username")
                .mail("test@mail.com")
                .password("test-pass")
                .role(new RoleDto("ROLE_USER"))
                .build();

        when(userService.getUserDtoById(userId)).thenReturn(userDto);

        mockMvc.perform(get("/api/v1/users/{id}", userId)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(userDto.getUsername()))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    public void when_updateUser_called_should_return_status200() throws Exception {
        Long userId = 1L;
        UserDto userDto = UserDto
                .builder()
                .id(userId)
                .firstName("test-firstname")
                .lastName("test-lastname")
                .username("test-username")
                .mail("test@mail.com")
                .password("test-pass")
                .role(new RoleDto("ROLE_USER"))
                .build();

        when(userService.updateUser(userId, userDto)).thenReturn(userDto);

        mockMvc.perform(put("/api/v1/users/{id}", userId)
                        .content(objectMapper.writeValueAsString(userDto))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(userDto.getUsername()))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    public void when_deleteUser_called_should_return_status200() throws Exception {
        Long userId = 1L;
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        when(userService.deleteUser(userId)).thenReturn(response);

        mockMvc.perform(delete("/api/v1/users/{id}", userId)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deleted").value(Boolean.TRUE))
                .andDo(print());
    }
}
