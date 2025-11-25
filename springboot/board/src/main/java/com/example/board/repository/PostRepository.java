package com.example.board.repository;

import com.example.board.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    //1. 쿼리 메서드
    List<Post> findByTitleContaining(String keyword);

    //2. JPQL(Java Persistence Query Language)
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% ORDER BY p.createdAt DESC")
    List<Post> findByTitleJPQL(@Param("keyword") String keyword);

    //3. native query
    @Query(value = "SELECT * FROM post WHERE title LIKE %:keyword: ORDER BY created_at DESC", nativeQuery = true)
    List<Post> findByTitleNativeQuery(@Param("keyword") String keyword);

    // 페이징 처리
    // JpaRepository가 구현 해둔 메서드 오버로딩
    Page<Post> findAll(Pageable pageable);

    // 무한스크롤(Slice)
    Slice<Post> findAllBy(Pageable pageable);
}
