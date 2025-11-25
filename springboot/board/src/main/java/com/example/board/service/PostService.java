package com.example.board.service;

import com.example.board.entity.Post;
import com.example.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Post createPost(Post post){
        return postRepository.save(post);
    }

    public Post getPostById(Long id){
        return postRepository.findById(id);
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    @Transactional
    public Post update(Long id, Post updatedPost){
        Post post = getPostById(id);
        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        return postRepository.update(post);
    }

    @Transactional
    public void deletePost(Post post){
        postRepository.deleteById(post);
    }

    public List<Post> searchPost(String keyword){
        return postRepository.findByTitleContaining(keyword);
    }

}
