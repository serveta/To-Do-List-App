package com.servetarslan.todolistapp.repository;

import com.servetarslan.todolistapp.model.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoListRepository extends JpaRepository<ToDoList, Long> {
}
