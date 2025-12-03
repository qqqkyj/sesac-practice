package com.example.instagram.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "posts")
@Entity
public class Post extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @Builder
    public Post(User user, String content) {
        this.user = user;
        this.content = content;
    }
}
