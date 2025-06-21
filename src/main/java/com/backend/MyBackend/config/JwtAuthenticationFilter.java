package com.backend.MyBackend.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain)
            throws ServletException, IOException{

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7); // Remove "Bearer "

            if (JwtUtil.validateToken(token)){
                String username = JwtUtil.getUsernameFromToken(token);

                // Extract roles from token
                Claims claims = JwtUtil.getAllClaimsFromToken(token);
                List<String> roles = claims.get("roles",List.class);

                // Convert roles to Spring authorities
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // Important: prefix with ROLE_
                        .collect(Collectors.toList());

                // Set authentication manually
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,
                        null,authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } else{
            logger.error("No valid Authorization header found.");
        }

        filterChain.doFilter(request,response);
    }
}
