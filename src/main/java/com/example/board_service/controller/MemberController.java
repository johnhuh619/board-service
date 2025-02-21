package com.example.board_service.controller;

import com.example.board_service.domain.Member;
import com.example.board_service.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<Member> register(@RequestBody Member member) {
        Member registeredMember = memberService.register(member);
        return new ResponseEntity<>(registeredMember, HttpStatus.CREATED);
    }

    // DTO 와 토큰 발급은 이후 코드를 업그레이드 하며 반영하는 구조로 수정 예정.
    // 지금은 배운 대로만 구현.
    @PostMapping("/login")
    public ResponseEntity<Member> login(@RequestBody Member loginRequest) {
        Member member = memberService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(member);
    }

    // 이 API 에서는 현재 인증된 사용자라고 가정하고 Get 한다.
    @GetMapping("/profile/{memberId}")
    public ResponseEntity<Member> getProfile(@PathVariable Long memberId) {
        Member member = memberService.getProfile(memberId);
        return ResponseEntity.ok(member);
    }

    @PutMapping("/profile/{memberId}")
    public ResponseEntity<Member> updateProfile(@PathVariable Long memberId, @RequestBody Member updatedInfo) {
        Member member = memberService.updateProfile(memberId, updatedInfo);
        return ResponseEntity.ok(member);
    }
}
