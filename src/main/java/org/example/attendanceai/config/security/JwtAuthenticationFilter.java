package org.example.attendanceai.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    // List of endpoints that don't require authentication
    private final List<String> PUBLIC_PATHS = Arrays.asList(
            "/api/v1/auth/register",
            "/api/v1/auth/authenticate",
            "/api/v1/auth/validate-token"

    );

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {

        // Skip filter for public endpoints
        if (shouldSkipFilter(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Check if we have JWT Token
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract Token
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt); // Extract User Email from JWT

        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Checking if user exists
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            // Checking if jwt token is valid
            if(jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // Setting Authentication
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean shouldSkipFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return PUBLIC_PATHS.stream().anyMatch(path::equals) || path.startsWith("/api/v1/auth/");
    }
}