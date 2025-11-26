package com.example.board.service;

import com.example.board.entity.Comment;
import com.example.board.entity.Post;
import com.example.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;

    public void createComment(Long postId, Comment comment) {
        Post post = postService.getPostById(postId);
        post.addComment(comment);
        comment.setPost(post);
        commentRepository.save(comment);
    }
}
