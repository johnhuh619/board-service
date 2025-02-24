package com.example.board_service.controller;

import com.example.board_service.domain.Member;
import com.example.board_service.service.AuthService;
import com.example.board_service.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Member loginRequest) {
        Member member = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (!member.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        String token = jwtUtil.generateToken(member.getUsername());
        return ResponseEntity.ok(token);
    }
}
