package com.servetarslan.todolistapp.service.impl;

import com.servetarslan.todolistapp.dto.ItemCreateDto;
import com.servetarslan.todolistapp.dto.ItemDto;
import com.servetarslan.todolistapp.exception.ResourceNotFoundException;
import com.servetarslan.todolistapp.model.Item;
import com.servetarslan.todolistapp.model.ToDoList;
import com.servetarslan.todolistapp.repository.ItemRepository;
import com.servetarslan.todolistapp.service.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ToDoListServiceImpl toDoListServiceImpl;

    @Override
    public ItemCreateDto createItem(Long userId, Long toDoListId, ItemCreateDto itemCreateDto) {
        ToDoList toDoList = toDoListServiceImpl.getToDoListByIdAndUserIdIfExist(toDoListId, userId);
        Item item = DtoToEntity(itemCreateDto);
        item.setToDoList(toDoList);
        Item result = itemRepository.save(item);
        return EntityToCreateDto(result);
    }

    @Override
    public List<ItemDto> getAllItems(Long userId, Long toDoListId) {
        toDoListServiceImpl.getToDoListByIdAndUserIdIfExist(toDoListId, userId);
        List<ItemDto> itemDtoList = new ArrayList<>();
        Iterable<Item> itemIterable = itemRepository.findAllByToDoListId(toDoListId);
        for (Item item : itemIterable) {
            ItemDto itemDto = EntityToDto(item);
            itemDtoList.add(itemDto);
        }
        return itemDtoList;
    }

    @Override
    public ItemDto getItemDtoById(Long userId, Long toDoListId, Long itemId) {
        toDoListServiceImpl.getToDoListByIdAndUserIdIfExist(toDoListId, userId);
        Item item = getItemByIdAndToDoListIdIfExist(itemId, toDoListId);
        return EntityToDto(item);
    }

    @Override
    public ItemDto updateItem(Long userId, Long toDoListId, Long itemId, ItemDto itemDto) {
        ToDoList toDoList = toDoListServiceImpl.getToDoListByIdAndUserIdIfExist(toDoListId, userId);
        Item existItem = getItemByIdAndToDoListIdIfExist(itemId, toDoListId);

        Item item = DtoToEntity(itemDto);

        existItem.setTitle(item.getTitle());
        existItem.setDescription(item.getDescription());
        existItem.setExpiration(item.getExpiration());
        existItem.setToDoList(toDoList);

        Item itemUpdate = itemRepository.save(existItem);
        return EntityToDto(itemUpdate);
    }

    @Override
    public Map<String, Boolean> deleteItem(Long userId, Long toDoListId, Long itemId) {
        toDoListServiceImpl.getToDoListByIdAndUserIdIfExist(toDoListId, userId);
        Item item = getItemByIdAndToDoListIdIfExist(itemId, toDoListId);
        itemRepository.delete(item);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    private Item getItemByIdAndToDoListIdIfExist(Long itemId, Long toDoListId) {
        return itemRepository.findByIdAndToDoListId(itemId, toDoListId)
                .orElseThrow(() -> new ResourceNotFoundException("Item does not found!"));
    }

    private ItemDto EntityToDto(Item item) {
        return modelMapper.map(item, ItemDto.class);
    }

    private ItemCreateDto EntityToCreateDto(Item item) {
        return modelMapper.map(item, ItemCreateDto.class);
    }

    private Item DtoToEntity(ItemDto itemDto) {
        return modelMapper.map(itemDto, Item.class);
    }

    private Item DtoToEntity(ItemCreateDto itemCreateDto) {
        return modelMapper.map(itemCreateDto, Item.class);
    }
}
