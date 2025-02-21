package com.example.board_service.service;

import com.example.board_service.domain.Comment;
import com.example.board_service.domain.Member;
import com.example.board_service.domain.Post;
import com.example.board_service.repository.CommentRepository;
import com.example.board_service.repository.MemberRepository;
import com.example.board_service.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final MemberRepository memberRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, MemberRepository memberRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public Comment createComment(Comment comment, Long authorId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("post가 id로 조회되지 않습니다. post id: " + postId));
        Member author = memberRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("member가 id로 조회되지 않습니다. member id: " + authorId));
        comment.setPost(post);
        comment.setAuthor(author);
        comment.setCreatedDate(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Comment updateComment(Long commentId, Long authorId, String newContent) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("comment가 id로 조회되지 안습니다. commentId: " + commentId));
        if (!comment.getAuthor().getId().equals(authorId)) {
            throw new RuntimeException("이 comment에 대한 수정 권한이 없습니다.");
        }
        comment.setContent(newContent);
        comment.setCreatedDate(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId, Long authorId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new RuntimeException("comment가 id로 조회되지 안습니다. commentId: " + commentId));
        if (!comment.getAuthor().getId().equals(authorId)) {
            throw new RuntimeException("작성자가 일치하지 않습니다. 삭제 권한이 없습니다.");
        }
        commentRepository.delete(comment);
    }


}
