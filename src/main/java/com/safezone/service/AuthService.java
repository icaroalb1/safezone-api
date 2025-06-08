package com.safezone.service;

import com.safezone.dto.LoginRequest;
import com.safezone.dto.RegisterRequest;

public interface AuthService {
    String register(RegisterRequest request);
    String login(LoginRequest request);
} 