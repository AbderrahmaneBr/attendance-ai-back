package org.example.attendanceai.services.auth.impl;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.attendanceai.config.security.JwtService;
import org.example.attendanceai.services.auth.AuthContextService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthContextServiceImpl implements AuthContextService {
    private final HttpServletRequest request;
    private final JwtService jwtService;

    public Long getCurrentUserId() {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = header.substring(7);
        return jwtService.extractUserId(token);
    }

}
