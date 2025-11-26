package com.example.board.controller;

import com.example.board.dto.CommentDTO;
import com.example.board.dto.PostDTO;
import com.example.board.entity.Comment;
import com.example.board.entity.Post;
import com.example.board.repository.PostRepository;
import com.example.board.service.CommentService;
import com.example.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
//    private final PostRepository postRepository;
    private final PostService postService;
    private final CommentService commentService;

//    @GetMapping
//    public String list(Model model) {
//        List<Post> posts = postService.getAllPosts();
//        model.addAttribute("posts", posts);
//        return "posts/list";
//    }

    //더미 데이터 샘플 생성
    @GetMapping("/dummy")
    public String dummy(){
        postService.createDummyPost(100);
        return "redirect:/posts";
    }

    //페이징 처리
//    @GetMapping
//    public String list(@PageableDefault(size = 15, sort = "id" ,direction = Sort.Direction.DESC) Pageable pageable,
//                       Model model){
//        Page<Post> postPage = postService.getPostPage(pageable);
//
//        int currentPage = pageable.getPageNumber();
//        int totalPages = postPage.getTotalPages();
//
//        int displayPages = 5;
//        int startPage = Math.max(0, currentPage - displayPages/2);
//        int endPage = Math.min(startPage + displayPages - 1, totalPages - 1);
//        startPage = Math.max(0, endPage - displayPages + 1);
//
//        model.addAttribute("postPage",postPage);
//        model.addAttribute("startPage",startPage);
//        model.addAttribute("endPage",endPage);
//        return "posts/list";
//    }

    // 리스트 + 검색
    @GetMapping
    public String list(@PageableDefault(size = 15, sort = "id" ,direction = Sort.Direction.DESC) Pageable pageable,
                       @RequestParam(required = false) String keyword,
                       Model model){

        Page<Post> postPage;

        if(keyword == null || keyword.isBlank()) postPage = postService.getPostPage(pageable);
        else postPage = postService.getPostPageByTitle(keyword, pageable);

        int currentPage = pageable.getPageNumber();
        int displayPage = 5;
        int startPage = Math.max(0, currentPage - displayPage/2);
        int endPage = Math.min(startPage + displayPage - 1, postPage.getTotalPages() - 1);
        startPage = Math.max(0, endPage - displayPage + 1);

        model.addAttribute("postPage", postPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("keyword", keyword);

        return "posts/list";
    }


    //스크롤 처리(Slice)
    @GetMapping("/more")
    public String more(@PageableDefault(size = 15, sort = "id" ,direction = Sort.Direction.DESC) Pageable pageable,
                           Model model){
        Slice<Post> postSlice = postService.getPostSlice(pageable);
        model.addAttribute("postSlice",postSlice);
        return "posts/list-more";
    }

    //생성화면 렌더링
    @GetMapping("/new")
    public String newPost(Model model) {
        model.addAttribute("post", new PostDTO());
        return "posts/form";
    }

    //실제 생성
    @PostMapping
    public String create(@ModelAttribute Post post) {
        postService.createPost(post);
        return "redirect:/posts";
    }

    //상세페이지, 댓글 생성 및 목록 표시 렌더링
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        model.addAttribute("comment", new CommentDTO());

        List<Comment> comments = commentService.getCommentsByPostId(id);
        model.addAttribute("comments", comments);
        return "posts/detail";
    }

    //실제 댓글 생성
    @PostMapping("/{postId}/comments")
    public String commentCreate(@PathVariable Long postId, @ModelAttribute Comment comment) {
        commentService.createComment(postId, comment);
        return "redirect:/posts/" + postId;
    }

    //실제 댓글 삭제
    @PostMapping("/{postId}/comments/{cId}/delete")
    public String commentDelete(@PathVariable Long postId, @PathVariable Long cId) {
        commentService.deleteComment(cId);
        return "redirect:/posts/" + postId;
    }

    //Fetch Join Test
    @GetMapping("/fetch")
    public String fetch(Model model){
        model.addAttribute("posts", postService.getAllPostsWithEntityGraph());
        return "posts/list-test";
    }

    //수정화면 렌더링
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "posts/form";
    }

    //실제 수정
    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Post post) {
        postService.update(id, post);
        return "redirect:/posts/"+id;
    }

    //삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        postService.deletePost(post);
        return "redirect:/posts";
    }

    //검색
    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model) {
        List<Post> posts = postService.searchPost(keyword);
        model.addAttribute("posts", posts);
        return "posts/list";
    }

}
