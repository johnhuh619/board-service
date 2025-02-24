package com.example.board_service.service;

import com.example.board_service.domain.Member;

public interface AuthService {
    Member login(String username, String password);
}
