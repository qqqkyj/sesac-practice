package com.example.instagram.service;

import com.example.instagram.entity.Like;
import com.example.instagram.entity.Post;
import com.example.instagram.entity.User;
import com.example.instagram.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly=true)
@Service
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final PostService postService;
    private final UserService userService;


    @Transactional
    @Override
    public void toggleLike(Long postId, Long userId) {
        Optional<Like> existLike = likeRepository.findByPostIdAndUserId(postId, userId);
        if(existLike.isPresent()) {
            likeRepository.delete(existLike.get());
        }
        else{
            Post post = postService.findById(postId);
            User user = userService.findById(userId);
            Like like = Like.builder()
                    .post(post)
                    .user(user)
                    .build();
            likeRepository.save(like);
        }
    }

    @Override
    public boolean isLiked(Long postId, Long userId) {
        return likeRepository.existsByPostIdAndUserId(postId, userId);
    }

    @Override
    public long getLikeCount(Long postId) {
        return likeRepository.countByPostId(postId);
    }
}
