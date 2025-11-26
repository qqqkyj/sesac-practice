package com.example.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;
import java.util.List;

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
//    @BatchSize(size = 100) //local
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments;
    //생성 시점에 자동으로 현재 시간을 설정
    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    //연관관계 편의메서드
    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setPost(null);
    }
}
