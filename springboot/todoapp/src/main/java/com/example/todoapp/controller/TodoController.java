package com.example.todoapp.controller;

import com.example.todoapp.constant.TodoStatus;
import com.example.todoapp.dto.TodoDTO;
import com.example.todoapp.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public String todos(Model model) {
        model.addAttribute("todos", todoService.getAllTodos());
        model.addAttribute("todoCount", todoService.getTotalCount());
        model.addAttribute("completedCount", todoService.getCompletedCount());
        model.addAttribute("activeCount", todoService.getActiveCount());
        return "todos";
    }

    //생성 화면 렌더링
    @GetMapping("/new")
    public String todosNew(Model model)
    {
        model.addAttribute("todo", new TodoDTO());
        return "form";
    }

    //실제 생성
    @PostMapping
    public String create(@ModelAttribute TodoDTO todo,
                         RedirectAttributes redirectAttributes) {
        try {
            todoService.createTodo(todo);
            redirectAttributes.addFlashAttribute("message","todo created");
            return "redirect:/todos";
        }
        catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("message",e.getMessage());
            redirectAttributes.addFlashAttribute("status", TodoStatus.DANGER.getCode());
            return "redirect:/todos/new";
        }
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model){
        try{
            model.addAttribute("todo", todoService.getTodoById(id));
        }catch(IllegalArgumentException e){
            return "redirect:/todos";
        }
        return "detail";
    }

    //수정 화면 렌더링
    @GetMapping("/{id}/update")
    public String edit(@PathVariable Long id, Model model){
        try{
            model.addAttribute("todo", todoService.getTodoById(id));
        }catch(IllegalArgumentException e){
            return "redirect:/todos";
        }
        return "form";
    }

    //실제 수정
    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id,
                         @ModelAttribute TodoDTO todo,
                         RedirectAttributes redirectAttributes){
        try{
            todoService.updateTodoById(id, todo);
            redirectAttributes.addFlashAttribute("message", "todo updated");
            redirectAttributes.addFlashAttribute("status", TodoStatus.WARNING.getCode());
        }catch(IllegalArgumentException e){
            if(e.getMessage().contains("title length")){
                redirectAttributes.addFlashAttribute("message", e.getMessage());
                redirectAttributes.addFlashAttribute("status", TodoStatus.DANGER.getCode());
                return "redirect:/todos/" + id + "/update";
            }
            return "redirect:/todos";
        }
        return "redirect:/todos/" + id;
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirectAttributes){
        todoService.deleteTodoById(id);
        redirectAttributes.addFlashAttribute("message","todo deleted");
        redirectAttributes.addFlashAttribute("status", TodoStatus.DANGER.getCode());
        return "redirect:/todos";
    }

    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model){
        model.addAttribute("todos", todoService.searchTodosByTitle(keyword));
        return "/todos";
    }

    @GetMapping("/active")
    public String active(Model model){
        model.addAttribute("todos", todoService.getTodosByCompleted(false));
        return "/todos";
    }

    @GetMapping("/completed")
    public String completed(Model model){
        model.addAttribute("todos", todoService.getTodosByCompleted(true));
        return "/todos";
    }

    @GetMapping("/{id}/toggle")
    public String toggle(@PathVariable Long id){
        try {
            todoService.toggleCompleted(id);
            return "redirect:/todos/" + id;
        }catch(IllegalArgumentException e){
            return "redirect:/todos";
        }
    }

    @GetMapping("/delete-completed")
    public String deleteCompleted(RedirectAttributes redirectAttributes){
        try{
            todoService.deleteCompletedTodos();
            redirectAttributes.addFlashAttribute("message","완료된 할 일 전체 삭제 성공!");
        }
        catch(IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("message",e.getMessage());
        }
        redirectAttributes.addFlashAttribute("status", TodoStatus.DANGER.getCode());
        return "redirect:/todos";
    }

}
