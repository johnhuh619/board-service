package com.example.board_service.repository;

import com.example.board_service.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
//    List<Post> findAuthorById(Long authorId);
}
