package com.example.instagram.controller;

import com.example.instagram.dto.response.PostResponse;
import com.example.instagram.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    @GetMapping("/")
    public String home(Model model) {
        //게시글 전체 조회
        List<PostResponse> postResponses = postService.getAllPosts();
        model.addAttribute("posts",postResponses);
        return "home";
    }
}
