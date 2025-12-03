package com.example.instagram.service;

import com.example.instagram.dto.request.PostCreateRequest;
import com.example.instagram.dto.response.PostResponse;
import com.example.instagram.entity.Post;
import com.example.instagram.entity.User;
import com.example.instagram.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    @Transactional
    @Override
    public PostResponse create(PostCreateRequest postCreateRequest, Long userId) {
        User user =  userService.findById(userId);
        Post post = Post.builder()
                .content(postCreateRequest.getContent())
                .user(user)
                .build();
        Post saved =  postRepository.save(post);
        return PostResponse.from(saved);
    }
}
