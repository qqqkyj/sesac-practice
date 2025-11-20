package com.example.todoapp.service;

import com.example.todoapp.dto.TodoDTO;
import com.example.todoapp.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoDTO> getAllTodos(){
        return todoRepository.findAll();
    }

    public TodoDTO getTodoById(Long id){
        return todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found id = "+id));
    }

    public void createTodo(TodoDTO todo){
        validateTitle(todo.getTitle());
        todoRepository.save(todo);
    }

    public void validateTitle(String title){
        if(title == null || title.trim().isEmpty() || title.trim().length() > 50){
            throw new IllegalArgumentException("title length should be between 1 and 50 characters");
        }
    }

    public void deleteTodoById(Long id){
        todoRepository.deleteById(id);
    }

    public void updateTodoById(Long id, TodoDTO newTodo){
        TodoDTO oldTodo = getTodoById(id);
        if(oldTodo == null)
            throw new IllegalArgumentException("not found id = "+id);
        else{
            validateTitle(newTodo.getTitle());
            todoRepository.save(newTodo);
        }
    }

    public List<TodoDTO> searchTodosByTitle(String keyword){
        return todoRepository.findByTitleContaining(keyword);
    }

    public List<TodoDTO> getTodosByCompleted(boolean completed){
        return todoRepository.findByCompleted(completed);
    }

    public TodoDTO toggleCompleted(Long id){
        TodoDTO todo = getTodoById(id);
        todo.setCompleted(!todo.isCompleted());
        return todoRepository.save(todo);
    }

    public void deleteCompletedTodos(){
        if(todoRepository.findByCompleted(true).size() <= 0){
            throw new IllegalArgumentException("완료한 일이 없습니다.");
        }
        todoRepository.deleteCompleted();
    }

    public long getTotalCount(){
        return todoRepository.findAll().size();
    }

    public long getCompletedCount(){
        return todoRepository.findByCompleted(true).size();
    }

    public long getActiveCount(){
        return todoRepository.findByCompleted(false).size();
    }
}
