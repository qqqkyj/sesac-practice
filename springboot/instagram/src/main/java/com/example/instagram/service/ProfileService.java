package com.example.instagram.service;

import com.example.instagram.dto.request.ProfileUpdateRequest;

public interface ProfileService {
    void updateProfile(Long id, ProfileUpdateRequest profileUpdateRequest);
}
