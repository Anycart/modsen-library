package com.modsen.registration.controller;

import com.modsen.registration.dto.AuthRequest;
import com.modsen.registration.dto.AuthResponse;
import com.modsen.registration.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name = "Registration controller", description = "Designed to obtain a JWT token")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/auth")
    @Operation(
            summary = "Getting JWT token"
    )
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }


}
