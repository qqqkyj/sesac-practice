package com.example.todoapp.repository;

import com.example.todoapp.dto.TodoDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TodoRepository {
    private final Map<Long, TodoDTO> storage = new ConcurrentHashMap<>();
    private Long nextId = 1L;

    public List<TodoDTO> findAll() {
        return new ArrayList<>(storage.values());
    }

    public void save(TodoDTO todo) {
        if(todo.getId() == null) {todo.setId(nextId++);}
        storage.put(todo.getId(), todo);
    }

    public Optional<TodoDTO> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public void deleteById(Long id) {
        storage.remove(id);
    }

    public List<TodoDTO> findByTitleContaining(String keyword){
        return storage.values().stream()
                .filter((todo) -> todo.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }

    public List<TodoDTO> findByCompleted(boolean isCompleted) {
        return storage.values().stream()
                .filter((todo) -> todo.isCompleted() == isCompleted)
                .toList();
    }

}
