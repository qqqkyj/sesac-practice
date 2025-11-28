package com.example.securitydemo.controller;

import com.example.securitydemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails user, Model model){
        if(user != null){
            model.addAttribute("username",user.getUsername());
            model.addAttribute("password",user.getPassword());
            model.addAttribute("roles",user.getAuthorities());
        }
        return "dashboard";
    }
}
