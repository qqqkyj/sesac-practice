package com.example.instagram.controller;

import com.example.instagram.dto.request.CommentRequest;
import com.example.instagram.dto.request.PostCreateRequest;
import com.example.instagram.dto.response.CommentResponse;
import com.example.instagram.dto.response.PostResponse;
import com.example.instagram.security.CustomUserDetails;
import com.example.instagram.service.CommentService;
import com.example.instagram.service.LikeService;
import com.example.instagram.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    private final LikeService likeService;

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

    //게시글 상세 페이지
    @GetMapping("{id}")
    public String detail(@PathVariable Long id, Model  model,
                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        PostResponse post = postService.getPostById(id);//게시물 전체 조회
        List<CommentResponse> comments = commentService.getAllCommentsByPostId(id);//댓글 전체 조회
        boolean liked = likeService.isLiked(id,userDetails.getId());
        long likeCount = likeService.getLikeCount(id);

        model.addAttribute("post",post);
        model.addAttribute("comments",comments);
        model.addAttribute("commentRequest", new CommentRequest());
        model.addAttribute("liked",liked);
        model.addAttribute("likeCount",likeCount);
        return "post/detail";
    }

    //실제 댓글 생성
    @PostMapping("{postId}/comments")
    public String createComment(@Valid @ModelAttribute CommentRequest commentRequest,
                                @PathVariable Long postId,
                                BindingResult bindingResult,
                                @AuthenticationPrincipal CustomUserDetails userDetails,
                                Model  model) {
        if(bindingResult.hasErrors()){
            PostResponse post = postService.getPostById(postId);
            List<CommentResponse> comments = commentService.getAllCommentsByPostId(postId);//전체 댓글 조회
            boolean liked = likeService.isLiked(postId,userDetails.getId());
            long likeCount = likeService.getLikeCount(postId);

            model.addAttribute("comments",comments);
            model.addAttribute("post",post);
            model.addAttribute("liked",liked);
            model.addAttribute("likeCount",likeCount);
            return "post/detail";
        }

        //댓글 생성
        commentService.create(postId, commentRequest, userDetails.getId());

        return "redirect:/posts/"+postId;
    }

    // 좋아요 기능
    @PostMapping("/{postId}/like")
    public String like(@PathVariable Long postId,
                       @AuthenticationPrincipal CustomUserDetails userDetails){
        likeService.toggleLike(postId, userDetails.getId());
        return "redirect:/posts/"+postId;
    }
}
