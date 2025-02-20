package com.example.board_service.service;

import com.example.board_service.domain.Member;
import com.example.board_service.domain.Post;
import com.example.board_service.repository.MemberRepository;
import com.example.board_service.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostServiceImpl(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public Post createPost(Post post, Long authorId) {
        Member author = memberRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("멤버가 id로 조회되지 않습니다. id: " + authorId));
        post.setAuthor(author);
        //title & content는 작성되어 post로 전달된다고 가정.
        post.setCreatedDate(LocalDateTime.now());
        post.setModifiedDate(LocalDateTime.now());
        post.setViewCount(0);
        return postRepository.save(post);
    }

    @Override
    @Transactional(readOnly = true)
    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("post가 조회되지 않습니다. post id: " + postId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    @Override
    @Transactional
    public Post updatePost(Long postId, Post updatedPost, Long authorId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("post가 조회되지 않습니다. post id: " + postId));
        if (!post.getAuthor().getId().equals(authorId)) {
            throw new RuntimeException("post에 대한 업데이트 권한이 없습니다.");
        }
        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        post.setModifiedDate(LocalDateTime.now());
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public void deletePost(Long postId, Long authorId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("post가 조회되지 않습니다. post id: " + postId));
        // 작성자 확인: 작성자 본인만 삭제 가능하도록 체크
        if (!post.getAuthor().getId().equals(authorId)) {
            throw new RuntimeException("이 post에 대한 삭제 권한이 없습니다.");
        }
        postRepository.delete(post);
    }

}
