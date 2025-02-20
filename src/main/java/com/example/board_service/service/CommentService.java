package com.example.board_service.service;

import com.example.board_service.domain.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(Comment comment, Long authorId, Long postId);

    Comment updateComment(Long commentId, Long authorId, String newContent);

    List<Comment> getCommentsByPostId(Long postId);

    void deleteComment(Long postId, Long commentId);
}
