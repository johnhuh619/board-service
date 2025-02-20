package com.example.board_service.service;

import com.example.board_service.domain.Member;
import com.example.board_service.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public Member register(Member member) {
        Optional<Member> exist = memberRepository.findByUsername(member.getUsername());
        if (exist.isPresent()) {
            throw new RuntimeException("이미 존재하는 사용자입니다.");
        }
        member.setJoinDate(LocalDateTime.now());
        return memberRepository.save(member);
    }

    @Override
    @Transactional(readOnly = true)
    public Member login(String username, String password) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public Member getProfile(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));
    }

    @Override
    @Transactional
    public Member updateProfile(Long memberId, Member updatedInfo) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new RuntimeException("회원이 존재하지 않습니다."));
        member.setEmail(updatedInfo.getEmail());
        member.setNickname(updatedInfo.getNickname());
        return memberRepository.save(member);
    }
}
