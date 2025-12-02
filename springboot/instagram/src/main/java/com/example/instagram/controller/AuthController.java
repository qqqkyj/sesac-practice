package com.example.instagram.controller;

import com.example.instagram.dto.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    //로그인 화면
    @GetMapping("/login")
    public String login(){
        return "auth/login";
    }

    //회원가입 화면
    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("signUpRequest", new SignUpRequest());
        return "auth/signup";
    }
}
