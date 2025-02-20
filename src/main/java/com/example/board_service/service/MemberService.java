package com.example.board_service.service;

import com.example.board_service.domain.Member;

public interface MemberService {
    Member register(Member member);

    Member login(String username, String password);

    Member getProfile(Long memberId);

    Member updateProfile(Long memberId, Member updatedInfo);

}
