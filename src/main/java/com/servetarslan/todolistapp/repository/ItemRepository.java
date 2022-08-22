package com.servetarslan.todolistapp.repository;

import com.servetarslan.todolistapp.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Iterable<Item> findAllByToDoListId(Long toDoListId);
    Optional<Item> findByIdAndToDoListId(Long itemId, Long toDoListId);
}
