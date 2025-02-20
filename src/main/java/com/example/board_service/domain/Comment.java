package com.example.board_service.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "comments")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;
    private LocalDateTime createdDate;

    // 연관관계: 게시글 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    // 연관관계: 댓글 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member author;

}
