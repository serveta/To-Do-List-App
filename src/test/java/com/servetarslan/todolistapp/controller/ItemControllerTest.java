package com.servetarslan.todolistapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.servetarslan.todolistapp.dto.ItemCreateDto;
import com.servetarslan.todolistapp.dto.ItemDto;
import com.servetarslan.todolistapp.security.JWTokenUtil;
import com.servetarslan.todolistapp.security.JwtAuthenticationEntryPoint;
import com.servetarslan.todolistapp.service.ItemService;
import com.servetarslan.todolistapp.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {
    @MockBean
    private ItemService itemService;
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
    public void when_createItem_called_should_return_status200() throws Exception {
        Long userId = 1L;
        Long toDoListId = 2L;
        ItemCreateDto itemCreateDto = ItemCreateDto
                .builder()
                .title("item-test")
                .description("item-description-test")
                .expiration(new Date())
                .build();

        when(itemService.createItem(userId, toDoListId, itemCreateDto)).thenReturn(itemCreateDto);

        mockMvc.perform(post("/api/v1/users/{userId}/todolists/{toDoListId}/items", userId, toDoListId)
                        .content(objectMapper.writeValueAsString(itemCreateDto))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(itemCreateDto.getTitle()))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    public void when_getAllItems_called_should_return_status200() throws Exception {
        Long userId = 1L;
        Long toDoListId = 2L;
        ItemDto itemDto1 = ItemDto
                .builder()
                .id(3L)
                .title("item-test-1")
                .description("item-description-test-1")
                .expiration(new Date())
                .build();
        ItemDto itemDto2 = ItemDto
                .builder()
                .id(4L)
                .title("item-test-2")
                .description("item-description-test-2")
                .expiration(new Date())
                .build();

        List<ItemDto> itemDtoList = new ArrayList<>();
        itemDtoList.add(itemDto1);
        itemDtoList.add(itemDto2);

        when(itemService.getAllItems(userId, toDoListId)).thenReturn(itemDtoList);

        mockMvc.perform(get("/api/v1/users/{userId}/todolists/{toDoListId}/items", userId, toDoListId)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].title").value(itemDtoList.get(1).getTitle()))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    public void when_getItemById_called_should_return_status200() throws Exception {
        Long userId = 1L;
        Long toDoListId = 2L;
        Long itemId = 3L;
        ItemDto itemDto = ItemDto
                .builder()
                .id(itemId)
                .title("item-test")
                .description("item-description-test")
                .expiration(new Date())
                .build();

        when(itemService.getItemDtoById(userId, toDoListId, itemId)).thenReturn(itemDto);

        mockMvc.perform(get("/api/v1/users/{userId}/todolists/{toDoListId}/items/{itemId}", userId, toDoListId, itemId)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(itemDto.getTitle()))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    public void when_updateItem_called_should_return_status200() throws Exception {
        Long userId = 1L;
        Long toDoListId = 2L;
        Long itemId = 3L;
        ItemDto itemDto = ItemDto
                .builder()
                .id(itemId)
                .title("item-test")
                .description("item-description-test")
                .expiration(new Date())
                .build();

        when(itemService.updateItem(userId, toDoListId, itemId, itemDto)).thenReturn(itemDto);

        mockMvc.perform(put("/api/v1/users/{userId}/todolists/{toDoListId}/items/{itemId}", userId, toDoListId, itemId)
                        .content(objectMapper.writeValueAsString(itemDto))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(itemDto.getTitle()))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    public void when_deleteItem_called_should_return_status200() throws Exception {
        Long userId = 1L;
        Long toDoListId = 2L;
        Long itemId = 3L;
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        when(itemService.deleteItem(userId, toDoListId, itemId)).thenReturn(response);

        mockMvc.perform(delete("/api/v1/users/{userId}/todolists/{toDoListId}/items/{itemId}", userId, toDoListId, itemId)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deleted").value(Boolean.TRUE))
                .andDo(print());
    }
}
