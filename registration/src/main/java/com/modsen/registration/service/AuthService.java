package com.modsen.registration.service;

import com.modsen.registration.dto.AuthRequest;
import com.modsen.registration.dto.AuthResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final JWTUtil jwtUtil;

    public AuthResponse register(AuthRequest request) {
        String accessToken = jwtUtil.generate(request.getName(), request.getSurname());
        return new AuthResponse(accessToken);
    }

}
