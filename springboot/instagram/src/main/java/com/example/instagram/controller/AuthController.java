package com.example.instagram.controller;

import com.example.instagram.dto.request.SignUpRequest;
import com.example.instagram.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    //로그인 화면
    @GetMapping("/login")
    public String login(){
        return "auth/login";
    }

    //회원가입 화면
    @GetMapping("/signup")
    public String signupForm(Model model){
        model.addAttribute("signUpRequest", new SignUpRequest());
        return "auth/signup";
    }

    //회원가입 시 사용자 정보를 받아서 DB에 저장
    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute SignUpRequest signUpRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "auth/signup";
        }

        //username 중복 확인(사용자 존재여부)
        if(userService.existsByUsername(signUpRequest.getUsername())){
            bindingResult.rejectValue("username", "duplicate", "이미 사용중인 아이디입니다.");
            return "auth/signup";
        }

        userService.register(signUpRequest);

        return "redirect:/auth/login";
    }
}
