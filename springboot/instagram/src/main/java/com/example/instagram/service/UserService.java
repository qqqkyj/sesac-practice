package com.example.instagram.service;

import com.example.instagram.dto.request.SignUpRequest;
import com.example.instagram.entity.User;

public interface UserService {
    boolean existsByUsername(String username);
    User register(SignUpRequest signUpRequest);
    User findById(Long userId);
}
