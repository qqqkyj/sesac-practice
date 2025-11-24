package com.example.board.controller;

import com.example.board.dto.PostDTO;
import com.example.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostRepository postRepository;

    @GetMapping
    public String list(Model model) {
        List<PostDTO> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "posts/list";
    }

    //생성화면 렌더링
    @GetMapping("/new")
    public String newPost(Model model) {
        model.addAttribute("post", new PostDTO());
        return "posts/form";
    }

    //실제 생성
    @PostMapping
    public String create(@ModelAttribute PostDTO post) {
        postRepository.save(post);
        return "redirect:/posts";
    }

    //상세페이지
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        PostDTO post = postRepository.findById(id);
        model.addAttribute("post", post);
        return "posts/detail";
    }

    //수정화면 렌더링
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        PostDTO post = postRepository.findById(id);
        model.addAttribute("post", post);
        return "posts/form";
    }

    //실제 수정
    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute PostDTO post) {
        postRepository.update(id, post);
        return "redirect:/posts/"+id;
    }

    //삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        postRepository.deleteById(id);
        return "redirect:/posts";
    }

}
