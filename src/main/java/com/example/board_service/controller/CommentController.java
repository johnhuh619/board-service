package com.example.board_service.controller;

import com.example.board_service.domain.Comment;
import com.example.board_service.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment, @RequestParam Long authorId, @RequestParam Long postId) {
            Comment createdComment = commentService.createComment(comment, postId, authorId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId, @RequestParam Long authorId, @RequestBody String newContent){
        Comment updatComment = commentService.updateComment(commentId, authorId, newContent);
        return ResponseEntity.ok(updatComment);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPost(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, @RequestParam Long authorId) {
        commentService.deleteComment(commentId, authorId);
        return ResponseEntity.noContent().build();
    }
}
