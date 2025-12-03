package com.example.instagram.controller;

import com.example.instagram.dto.request.PostCreateRequest;
import com.example.instagram.dto.response.PostResponse;
import com.example.instagram.security.CustomUserDetails;
import com.example.instagram.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    //게시글 폼 생성
    @GetMapping("/new")
    public String createForm(Model  model) {
        model.addAttribute("postCreateRequest",new PostCreateRequest());
        return "post/form";
    }

    //실제 게시글 생성
    @PostMapping
    public String create(@Valid @ModelAttribute PostCreateRequest postCreateRequest,
                         BindingResult bindingResult,
                         //현재 로그인한 사용자의 정보
                         @AuthenticationPrincipal CustomUserDetails userDetails){
        if(bindingResult.hasErrors()){
            return "post/form";
        }
        postService.create(postCreateRequest, userDetails.getId());
        return "redirect:/";
    }
}
