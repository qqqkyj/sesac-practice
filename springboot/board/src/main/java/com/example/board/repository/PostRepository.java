package com.example.board.repository;

import com.example.board.dto.PostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<PostDTO> rowMapper = (rs, rowNum) -> {
        return new PostDTO(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    };

    //생성
    public void save(PostDTO post){
        String sql = "INSERT INTO post (title, content) VALUES (?, ?)";
        jdbcTemplate.update(sql,post.getTitle(), post.getContent());
    }

    //조회
    public List<PostDTO> findAll(){
        String sql = "SELECT * FROM post ";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public PostDTO findById(Long id){
        String sql = "SELECT * FROM post WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    //수정
    public void update(Long id, PostDTO post){
        String sql = "UPDATE post SET title = ?, content = ? WHERE id = ?";
        jdbcTemplate.update(sql, post.getTitle(), post.getContent(), id);
    }

    //삭제
    public void deleteById(Long id){
        String sql = "DELETE FROM post WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
