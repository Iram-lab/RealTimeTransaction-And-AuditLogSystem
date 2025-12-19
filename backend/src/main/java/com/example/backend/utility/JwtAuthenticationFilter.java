package com.example.backend.utility;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtTokenUtil;

    public JwtAuthenticationFilter(JwtUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
protected void doFilterInternal(HttpServletRequest request,
                                HttpServletResponse response,
                                FilterChain chain)
        throws ServletException, IOException {

    String path = request.getServletPath();

    // IMPORTANT: Skip JWT filter for public endpoints
    if (path.startsWith("/api/auth/")) {
        chain.doFilter(request, response);
        return;
    }

    final String authHeader = request.getHeader("Authorization");
    String username = null;
    String token = null;

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
        token = authHeader.substring(7);
        try {
            username = jwtTokenUtil.extractUsername(token);
        } catch (Exception e) {
            System.out.println("JWT Error: " + e.getMessage());
        }
    }

    if (username != null &&
        SecurityContextHolder.getContext().getAuthentication() == null &&
        jwtTokenUtil.validateToken(token, username)) {

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        username, null, Collections.emptyList());

        authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    chain.doFilter(request, response);
}

}
