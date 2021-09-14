package com.todolist.todolist.repository;

import com.todolist.todolist.model.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoListRepository extends JpaRepository<ToDoList, Integer> {
    //List<ToDoList> findByUserId(User id);

    ToDoList findByIdAndUserId(int id, int user);
}
