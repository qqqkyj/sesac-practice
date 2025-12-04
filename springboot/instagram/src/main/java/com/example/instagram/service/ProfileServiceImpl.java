package com.example.instagram.service;

import com.example.instagram.dto.request.ProfileUpdateRequest;
import com.example.instagram.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserService userService;

    @Override
    public void updateProfile(Long userId, ProfileUpdateRequest profileUpdateRequest) {
        User user = userService.findById(userId);
        user.updateProfile(profileUpdateRequest.getName(), profileUpdateRequest.getBio());
    }
}
