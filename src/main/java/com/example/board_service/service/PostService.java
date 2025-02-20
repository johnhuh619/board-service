package com.example.board_service.service;

import com.example.board_service.domain.Post;

import java.util.List;

public interface PostService {
    Post createPost(Post post, Long authorId);

    Post getPostById(Long postId);

    List<Post> getPosts();

    Post updatePost(Long postId, Post updatedPost, Long authorId);

    void deletePost(Long postId, Long authorId);

}
