package com.servetarslan.todolistapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.servetarslan.todolistapp.dto.ToDoListCreateDto;
import com.servetarslan.todolistapp.dto.ToDoListDto;
import com.servetarslan.todolistapp.security.JWTokenUtil;
import com.servetarslan.todolistapp.security.JwtAuthenticationEntryPoint;
import com.servetarslan.todolistapp.service.ToDoListService;
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

@WebMvcTest(ToDoListController.class)
@ExtendWith(MockitoExtension.class)
public class ToDoListControllerTest {
    @MockBean
    private ToDoListService toDoListService;
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
    @WithMockUser(username = "test", password = "test")
    public void when_createToDoList_called_should_return_status200() throws Exception {
        Long userId = 1L;
        ToDoListCreateDto toDoListCreateDto = ToDoListCreateDto
                .builder()
                .title("Test-title")
                .build();

        when(toDoListService.createToDoList(userId, toDoListCreateDto)).thenReturn(toDoListCreateDto);

        mockMvc.perform(post("/api/v1/users/{userId}/todolists", userId)
                .content(objectMapper.writeValueAsString(toDoListCreateDto))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(toDoListCreateDto.getTitle()))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    public void when_getAllToDoLists_called_should_return_status200() throws Exception {
        Long userId = 1L;
        List<ToDoListDto> toDoListDtos = new ArrayList<>();
        toDoListDtos.add(new ToDoListDto(1L, "test-title-1"));
        toDoListDtos.add(new ToDoListDto(2L, "test-title-2"));
        toDoListDtos.add(new ToDoListDto(3L, "test-title-3"));

        when(toDoListService.getAllToDoLists(userId)).thenReturn(toDoListDtos);

        mockMvc.perform(get("/api/v1/users/{userId}/todolists", userId)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].title").value(toDoListDtos.get(1).getTitle()))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    public void when_getToDoListById_called_should_return_status200() throws Exception {
        Long userId = 1L;
        Long toDoListId = 2L;
        ToDoListDto toDoListDto = new ToDoListDto(toDoListId, "test-title");

        when(toDoListService.getToDoListDtoById(userId, toDoListId)).thenReturn(toDoListDto);

        mockMvc.perform(get("/api/v1/users/{userId}/todolists/{toDoListId}", userId, toDoListId)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(toDoListDto.getTitle()))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    public void when_updateToDoList_called_should_return_status200() throws Exception {
        Long userId = 1L;
        Long toDoListId = 2L;
        ToDoListDto toDoListDto = new ToDoListDto(toDoListId, "test-title");

        when(toDoListService.updateToDoList(userId, toDoListId, toDoListDto)).thenReturn(toDoListDto);

        mockMvc.perform(put("/api/v1/users/{userId}/todolists/{toDoListId}", userId, toDoListId)
                        .content(objectMapper.writeValueAsString(toDoListDto))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(toDoListDto.getTitle()))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    public void when_deleteToDoList_called_should_return_status200() throws Exception {
        Long userId = 1L;
        Long toDoListId = 2L;
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        when(toDoListService.deleteToDoList(userId, toDoListId)).thenReturn(response);

        mockMvc.perform(delete("/api/v1/users/{userId}/todolists/{toDoListId}", userId, toDoListId)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deleted").value(Boolean.TRUE))
                .andDo(print());
    }
}
