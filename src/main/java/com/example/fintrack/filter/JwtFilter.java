package com.example.fintrack.filter;

import com.example.fintrack.entity.User;
import com.example.fintrack.repository.UserRepository;
import com.example.fintrack.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        System.out.println("Auth header: " + authHeader);

        User user = new User();

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String email = jwtUtil.extractUsername(token);
            System.out.println("Email from token: " + email);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                user = userRepository.findByEmail(email).orElse(null);
                System.out.println("User from DB: " + user);

                boolean tokenValid = user != null && jwtUtil.isTokenValid(token, user);
                System.out.println("Token valid: " + tokenValid);

                if (tokenValid) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("Authentication set in context");
                }
            }
        }

        request.setAttribute("id", user.getId());

        filterChain.doFilter(request, response);
    }
}
