package com.example.board_service.service;

import com.example.board_service.domain.Member;
import com.example.board_service.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService{
    private final MemberRepository memberRepository;

    public AuthServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Member login(String username, String password) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));
    }
}
