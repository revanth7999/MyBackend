package com.backend.MyBackend.config;

import com.backend.MyBackend.common.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        String path = request.getServletPath();

        return path.equals("/dev/auth/login") ||
                path.equals("/dev/auth/register") ||
                path.equals("/dev/auth/refresh") ||
                path.startsWith("/dev/auth/logout");
    }

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
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request,response);
    }
}
