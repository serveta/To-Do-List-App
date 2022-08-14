package com.servetarslan.todolistapp.repository;

import com.servetarslan.todolistapp.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Iterable<Item> findAllByToDoListId(Long toDoListId);
    Item findByIdAndToDoListId(Long itemId, Long toDoListId);
}
