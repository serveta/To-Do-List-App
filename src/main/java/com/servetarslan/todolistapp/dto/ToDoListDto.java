package com.servetarslan.todolistapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Log4j2
public class ToDoListDto {
    private Long Id;
    private String title;
    private ItemDto itemDto;
    private UserDto userDto;
}
