package com.example.todoapp.exception;

import com.example.todoapp.constant.TodoStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler{
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFound(
            ResourceNotFoundException e,
            RedirectAttributes redirectAttributes
    ){
        redirectAttributes.addFlashAttribute("status", TodoStatus.DANGER.getCode());
        redirectAttributes.addFlashAttribute("message",e.getMessage());
        return "redirect:/todos";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(
            IllegalArgumentException e,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request
    ){
        redirectAttributes.addFlashAttribute("status", TodoStatus.WARNING.getCode());
        redirectAttributes.addFlashAttribute("message",e.getMessage());

        // 현재 요청 URL로 redirect
        String referer = request.getHeader("Referer");
        if(referer != null && !referer.isEmpty()){
            return "redirect:" + referer;
        }

        return "redirect:/todos";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("message", e.getMessage());
        return "redirect:/todos";
    }
}
