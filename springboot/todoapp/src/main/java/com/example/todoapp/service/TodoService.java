package com.example.todoapp.service;

import com.example.todoapp.dto.TodoDTO;
import com.example.todoapp.entity.TodoEntity;
import com.example.todoapp.exception.ResourceNotFoundException;
import com.example.todoapp.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class TodoService {

    private final TodoRepository todoRepository;
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    private TodoDTO toDTO(TodoEntity entity) {
        return new TodoDTO(entity.getId(), entity.getTitle(), entity.getContent(), entity.isCompleted());
    }

    public List<TodoDTO> getAllTodos(){
        return todoRepository.findAll().stream().map(this::toDTO).toList();
    }

    public TodoEntity findEntityById(Long id){
        return todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found : id = "+id));
    }

    public TodoDTO getTodoById(Long id){
        return toDTO(findEntityById(id));
    }

    public void createTodo(TodoDTO todo){
        validateTitle(todo.getTitle());
        todoRepository.save(new TodoEntity(todo.getTitle(), todo.getContent(), false));
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
        TodoEntity entity = findEntityById(id);//트랜잭션 커밋시점에 DB에 반영, 별도의 save는 불필요
        validateTitle(newTodo.getTitle());
        entity.setTitle(newTodo.getTitle());
        entity.setContent(newTodo.getContent());
        entity.setCompleted(newTodo.isCompleted());
    }

    public List<TodoDTO> searchTodosByTitle(String keyword){
        return todoRepository.findByTitleContaining(keyword).stream()
                .map(this::toDTO)
                .toList();
    }

    public List<TodoDTO> getTodosByCompleted(boolean completed){
        return todoRepository.findByCompleted(completed).stream()
                .map(this::toDTO)
                .toList();
    }

    public TodoDTO toggleCompleted(Long id){
        TodoEntity entity = findEntityById(id);
        entity.setCompleted(!entity.isCompleted());//트랜잭션 커밋시점에 DB에 반영, 별도의 save는 불필요
        return toDTO(entity);
    }

    public void deleteCompletedTodos(){
        if(todoRepository.countByCompleted(true)<= 0){
            throw new IllegalArgumentException("완료한 일이 없습니다.");
        }
        todoRepository.deleteByCompleted(true);
    }

    public long getTotalCount(){
        return todoRepository.count();
    }

    public long getCompletedCount(){
        return todoRepository.countByCompleted(true);
    }

    public long getActiveCount(){
        return todoRepository.countByCompleted(false);
    }
}
