package com.backend.MyBackend.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class RateLimitFilter extends OncePerRequestFilter{

    private static final Logger log = LoggerFactory.getLogger(RateLimitFilter.class);
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    private Bucket createNewBucket(){
        Bandwidth limit = Bandwidth.classic(
                10,
                Refill.greedy(10,Duration.ofMinutes(1)));
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException{

        String path = request.getRequestURI();
        List<String> entries = List.of("/api/auth");

        if (!path.startsWith(entries.toString())){
            filterChain.doFilter(request,response);
            return;
        }

        String ip = request.getRemoteAddr();
        Bucket bucket = cache.computeIfAbsent(
                ip,
                k -> createNewBucket());

        if (bucket.tryConsume(1)){
            filterChain.doFilter(request,response);
        } else{
            response.setStatus(429);
            logger.warn(
                    "Rate limit exceeded for IP: " + ip);
            response.getWriter().write(
                    "Too many requests. Try again later.");
        }
    }
}
