package com.example.instagram.service;

import com.example.instagram.dto.request.PostCreateRequest;
import com.example.instagram.dto.response.PostResponse;

import java.util.List;

public interface PostService {
    PostResponse create(PostCreateRequest postCreateRequest, Long userId);
    List<PostResponse> getAllPosts();
    PostResponse getPostById(Long id);
}
