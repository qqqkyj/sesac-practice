package com.example.board.repository;

import com.example.board.dto.PostDTO;
import com.example.board.entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
//@RequiredArgsConstructor
//public class PostRepository {
//    private final JdbcTemplate jdbcTemplate;
//
//    private final RowMapper<PostDTO> rowMapper = (rs, rowNum) -> {
//        return new PostDTO(
//                rs.getLong("id"),
//                rs.getString("title"),
//                rs.getString("content"),
//                rs.getTimestamp("created_at").toLocalDateTime()
//        );
//    };
//
//    //생성
//    public void save(PostDTO post){
//        String sql = "INSERT INTO post (title, content) VALUES (?, ?)";
//        jdbcTemplate.update(sql,post.getTitle(), post.getContent());
//    }
//
//    //조회
//    public List<PostDTO> findAll(){
//        String sql = "SELECT * FROM post ";
//        return jdbcTemplate.query(sql, rowMapper);
//    }
//
//    public PostDTO findById(Long id){
//        String sql = "SELECT * FROM post WHERE id = ?";
//        return jdbcTemplate.queryForObject(sql, rowMapper, id);
//    }
//
//    //수정
//    public void update(Long id, PostDTO post){
//        String sql = "UPDATE post SET title = ?, content = ? WHERE id = ?";
//        jdbcTemplate.update(sql, post.getTitle(), post.getContent(), id);
//    }
//
//    //삭제
//    public void deleteById(Long id){
//        String sql = "DELETE FROM post WHERE id = ?";
//        jdbcTemplate.update(sql, id);
//    }
//}

@RequiredArgsConstructor
@Repository
public class PostRepository {
    @PersistenceContext
    private final EntityManager em;

    public List<Post> findAll(){
        String jpql="select p from Post p";
        return em.createQuery(jpql,Post.class).getResultList();
    }

    public  Post findById(int id)
    {
        return em.find(Post.class,id);
    }

    public Post save(Post post)
    {
        em.persist(post);
        return post;
    }

    public void update(Post post)
    {
        em.merge(post);
    }

    public void deleteById(Post post){
        em.remove(post);
    }

    public List<Post> findByTitleContaining(String keyword){
        String jpql="select p from Post p where p.title like :keyword";
        return em.createQuery(jpql,Post.class).setParameter("keyword", "%"+keyword+"%").getResultList();
    }
}
