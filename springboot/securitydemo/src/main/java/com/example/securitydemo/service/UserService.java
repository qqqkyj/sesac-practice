package com.example.securitydemo.service;

import com.example.securitydemo.entity.User;
import com.example.securitydemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
    boolean existsByUsername(String username);
}
