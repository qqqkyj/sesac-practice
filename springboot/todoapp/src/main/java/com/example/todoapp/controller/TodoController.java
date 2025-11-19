package com.example.todoapp.controller;

import com.example.todoapp.dto.TodoDTO;
import com.example.todoapp.repository.TodoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/todos")
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping
    public String todos(Model model) {
        model.addAttribute("todos", todoRepository.findAll());
        return "todos";
    }

    //생성 화면 렌더링
    @GetMapping("/new")
    public String todosNew() {
        return "new";
    }

    //실제 생성
    @PostMapping
    public String create(@RequestParam String title,
                         @RequestParam String content,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        todoRepository.save(new TodoDTO(null, title, content, false));
        redirectAttributes.addFlashAttribute("message","todo created");
        model.addAttribute("todos", todoRepository.findAll());
        return "redirect:/todos";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model){
        try{
            model.addAttribute("todo", todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("todo not found")));
        }catch(IllegalArgumentException e){
            return "redirect:/todos";
        }
        return "detail";
    }

    //수정 화면 렌더링
    @GetMapping("/{id}/update")
    public String edit(@PathVariable Long id, Model model){
        try{
            model.addAttribute("todo", todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("todo not found")));
        }catch(IllegalArgumentException e){
            return "redirect:/todos";
        }
        return "update";
    }

    //실제 수정
    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id,
                         @RequestParam String title,
                         @RequestParam String content,
                         @RequestParam(defaultValue = "false") boolean completed,
                         RedirectAttributes redirectAttributes){
        try{
            TodoDTO todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("todo not found"));
            todo.setTitle(title);
            todo.setContent(content);
            todo.setCompleted(completed);
            todoRepository.save(todo);
            redirectAttributes.addFlashAttribute("message", "todo updated");
            redirectAttributes.addFlashAttribute("status", "update");
        }catch(IllegalArgumentException e){
            return "redirect:/todos";
        }
        return "redirect:/todos/" + id;
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirectAttributes){
        todoRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message","todo deleted");
        redirectAttributes.addFlashAttribute("status", "delete");
        return "redirect:/todos";
    }

    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model){
        model.addAttribute("todos", todoRepository.findByTitleContaining(keyword));
        return "/todos";
    }

    @GetMapping("/active")
    public String active(Model model){
        model.addAttribute("todos", todoRepository.findByCompleted(false));
        return "/todos";
    }

    @GetMapping("/completed")
    public String completed(Model model){
        model.addAttribute("todos", todoRepository.findByCompleted(true));
        return "/todos";
    }

    @GetMapping("/{id}/toggle")
    public String toggle(@PathVariable Long id){
        try {
            TodoDTO todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("todo not found"));
            todo.setCompleted(!todo.isCompleted());
            todoRepository.save(todo);
            return "redirect:/todos/" + id;
        }catch(IllegalArgumentException e){
            return "redirect:/todos";
        }
    }

}
