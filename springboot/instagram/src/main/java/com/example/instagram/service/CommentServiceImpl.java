package com.example.instagram.service;

import com.example.instagram.dto.request.CommentCreateRequest;
import com.example.instagram.dto.response.CommentResponse;
import com.example.instagram.entity.Comment;
import com.example.instagram.entity.Post;
import com.example.instagram.entity.User;
import com.example.instagram.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements  CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;

    //댓글 생성
    @Override
    @Transactional
    public CommentResponse create(Long postId, CommentCreateRequest commentCreateRequest, Long userId) {
        User user = userService.findById(userId);
        Post post =  postService.findById(postId);
        Comment comment = Comment.builder()
                .content(commentCreateRequest.getContent())
                .user(user)
                .post(post)
                .build();
        Comment saved = commentRepository.save(comment);
        return CommentResponse.from(saved);
    }

    @Override
    public List<CommentResponse> getAllCommentsByPostId(Long postId) {
        return commentRepository.findAllByPostIdOrderByCreatedAtDesc(postId)
                .stream()
                .map(CommentResponse::from)
                .toList();
    }
}
