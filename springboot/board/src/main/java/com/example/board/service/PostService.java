package com.example.board.service;

import com.example.board.entity.Post;
import com.example.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
        return postRepository.findById(id).orElseThrow(() -> (new IllegalArgumentException("post not found!")));
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    @Transactional
    public Post update(Long id, Post updatedPost){
        Post post = getPostById(id);
        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Post post){
        postRepository.delete(post);
    }

    public List<Post> searchPost(String keyword){
        return postRepository.findByTitleContaining(keyword);
    }

    //더미 데이터 샘플 생성
    @Transactional
    public void createDummyPost(int count){
        for(int i = 1; i <= count; i++){
            Post post = new Post(i+"번째 제목", i+"번째 게시물 내용");
            postRepository.save(post);
        }
    }

    //페이징 처리
    public Page<Post> getPostPage(Pageable pageable){
        return postRepository.findAll(pageable);
    }

    //스크롤 처리
    public Slice<Post> getPostSlice(Pageable pageable){
        return postRepository.findAllBy(pageable);
    }

    //검색 페이징 처리
    public Page<Post> getPostPageByTitle(String keyword, Pageable pageable){
        return postRepository.findByTitleContaining(keyword, pageable);
    }

    //Fetch Join test
    public List<Post> getAllPostsWithFetchJoin(){
        return postRepository.findAllWithCommentsFetchJoin();
    }

    public List<Post> getAllPostsWithEntityGraph(){
        return postRepository.findAllWithCommentsEntityGraph();
    }
}
