package com.servetarslan.todolistapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Log4j2
public class ItemDto {
    private Long id;
    private String title;
    private String description;
    private Date expiration;
    private Set<ToDoListDto> toDoListDto;
}
