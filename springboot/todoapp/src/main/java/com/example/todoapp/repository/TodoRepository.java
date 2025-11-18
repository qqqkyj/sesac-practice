package com.example.todoapp.repository;

import com.example.todoapp.dto.TodoDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TodoRepository {
    private final Map<Long, TodoDTO> storage = new ConcurrentHashMap<>();
    private Long nextId = 1L;

    public List<TodoDTO> getTodos() {
        return new ArrayList<>(storage.values());
    }

    public void save(TodoDTO todo) {
        if(todo.getId() == null) {todo.setId(nextId++);}
        storage.put(todo.getId(), todo);
    }

    public TodoDTO getTodoById(Long id) {
        return storage.get(id);
    }

    public void  delete(Long id) {
        storage.remove(id);
    }

}
