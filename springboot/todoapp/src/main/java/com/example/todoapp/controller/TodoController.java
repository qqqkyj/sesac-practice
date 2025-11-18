package com.example.todoapp.controller;

import com.example.todoapp.dto.TodoDTO;
import com.example.todoapp.repository.TodoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/todos")
    public String todos(Model model) {
        model.addAttribute("todos", todoRepository.getTodos());
        return "todos";
    }

    @GetMapping("/todos/new")
    public String todosNew() {
        return "new";
    }

    @GetMapping("/todos/create")
    public String create(@RequestParam String title, @RequestParam String content){
        todoRepository.save(new TodoDTO(null, title, content, false));
        return "redirect:/todos";
    }

    @GetMapping("/todos/{id}")
    public String detail(@PathVariable Long id, Model model){
        model.addAttribute("todo", todoRepository.getTodoById(id));
        return "detail";
    }

    @GetMapping("/todos/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("todo", todoRepository.getTodoById(id));
        return "edit";
    }

    @GetMapping("/todos/{id}/update")
    public String update(@PathVariable Long id,
                         @RequestParam String title,
                         @RequestParam String content,
                         @RequestParam(defaultValue = "false") boolean completed){
        todoRepository.save(new TodoDTO(id, title, content, completed));
        return "redirect:/todos/" + id;
    }

    @GetMapping("/todos/{id}/delete")
    public String delete(@PathVariable Long id){
        todoRepository.delete(id);
        return "redirect:/todos";
    }
}
