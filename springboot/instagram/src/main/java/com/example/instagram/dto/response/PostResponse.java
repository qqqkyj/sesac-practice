package com.example.instagram.dto.response;

import com.example.instagram.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponse {
    private Long id;
    private String content;
    private LocalDateTime createdAt;

    private Long userId;
    private String username;

    //Entity to DTO
    public static PostResponse from(Post post){
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .userId(post.getUser().getId())
                .username(post.getUser().getUsername())
                .createdAt(post.getCreatedDate())
                .build();
    }
}
