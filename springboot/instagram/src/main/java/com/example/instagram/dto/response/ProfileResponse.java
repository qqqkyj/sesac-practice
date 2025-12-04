package com.example.instagram.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileResponse {
    private Long id;
    private String username;
    private String name;
    private String bio;


}
