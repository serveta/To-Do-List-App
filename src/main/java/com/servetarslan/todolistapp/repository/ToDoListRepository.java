package com.servetarslan.todolistapp.repository;

import com.servetarslan.todolistapp.dto.ToDoListDto;
import com.servetarslan.todolistapp.model.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ToDoListRepository extends JpaRepository<ToDoList, Long> {
    List<ToDoList> findAllByUserId(Long userId);
    Optional<ToDoList> findByIdAndUserId(Long toDoList, Long userId);
}
