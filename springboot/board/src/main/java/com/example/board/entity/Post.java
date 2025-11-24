package com.example.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Table
@Entity
@NoArgsConstructor
public class Post
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100,  nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
