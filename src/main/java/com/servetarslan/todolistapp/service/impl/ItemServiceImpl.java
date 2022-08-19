package com.servetarslan.todolistapp.service.impl;

import com.servetarslan.todolistapp.dto.ItemCreateDto;
import com.servetarslan.todolistapp.dto.ItemDto;
import com.servetarslan.todolistapp.exception.ResourceNotFoundException;
import com.servetarslan.todolistapp.model.Item;
import com.servetarslan.todolistapp.repository.ItemRepository;
import com.servetarslan.todolistapp.service.ItemService;
import com.servetarslan.todolistapp.service.ToDoListService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ToDoListService toDoListService;

    @Override
    public ItemCreateDto createItem(Long userId, Long toDoListId, ItemCreateDto itemCreateDto) {
        if (toDoListService.getToDoListById(userId, toDoListId) != null) {
            Item item = DtoToEntity(itemCreateDto);
            item.setToDoList(toDoListService.getToDoListById(toDoListId));
            itemRepository.save(item);
            return itemCreateDto;
        }
        return null;
    }

    @Override
    public List<ItemDto> getAllItems(Long userId, Long toDoListId) {
        if (toDoListService.getToDoListById(userId, toDoListId) != null) {
            List<ItemDto> itemDtoList = new ArrayList<>();
            Iterable<Item> itemIterable = itemRepository.findAllByToDoListId(toDoListId);
            for (Item item : itemIterable) {
                ItemDto itemDto = EntityToDto(item);
                itemDtoList.add(itemDto);
            }
            return itemDtoList;
        }
        return null;
    }

    @Override
    public ResponseEntity<ItemDto> getItemById(Long userId, Long toDoListId, Long itemId) {
        if (toDoListService.getToDoListById(userId, toDoListId) != null) {
            Item item = itemRepository.findByIdAndToDoListId(itemId, toDoListId);
            if (item == null) throw new ResourceNotFoundException("item does not found!");
            ItemDto itemDto = EntityToDto(item);
            return ResponseEntity.ok(itemDto);
        }
        return null;
    }

    @Override
    public ResponseEntity<ItemDto> updateItem(Long itemId, Long toDoListId, Long userId, ItemDto itemDto) {
        Item itemEntity = DtoToEntity(itemDto);

        Item item = DtoToEntity(getItemById(userId, toDoListId, itemId).getBody());

        item.setTitle(itemEntity.getTitle());
        item.setDescription(itemEntity.getDescription());
        item.setExpiration(itemEntity.getExpiration());
        item.setToDoList(toDoListService.getToDoListById(toDoListId));

        Item itemUpdate = itemRepository.save(item);
        ItemDto responseItemDto = EntityToDto(itemUpdate);
        return ResponseEntity.ok(responseItemDto);
    }

    @Override
    public ResponseEntity<Map<String, Boolean>> deleteItem(Long itemId, Long toDoListId, Long userId) {
        Item item = DtoToEntity(getItemById(userId, toDoListId, itemId).getBody());
        itemRepository.delete(item);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    private ItemDto EntityToDto(Item item) {
        return modelMapper.map(item, ItemDto.class);
    }

    private Item DtoToEntity(ItemDto itemDto) {
        return modelMapper.map(itemDto, Item.class);
    }

    private Item DtoToEntity(ItemCreateDto itemCreateDto) {
        return modelMapper.map(itemCreateDto, Item.class);
    }
}
