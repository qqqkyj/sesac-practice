package com.example.board.service;

import com.example.board.entity.Comment;
import com.example.board.entity.Post;
import com.example.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;

    @Transactional
    public void createComment(Long postId, Comment comment) {
        Post post = postService.getPostById(postId);
        post.addComment(comment);
        comment.setPost(post);
        commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPostId(Long  postId) {
        return commentRepository.findCommentsByPostId(postId);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("comment not found"));
        //고아 객체 삭제
        Post post = comment.getPost();
        post.removeComment(comment);
        commentRepository.delete(comment);
    }
}
