package com.backend.MyBackend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class LoggingFilter extends OncePerRequestFilter{

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String CYAN = "\u001B[36m";
    private static final String RESET = "\u001B[0m";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException{

        long start = System.currentTimeMillis();

        String method = request.getMethod();
        String methodColor = getMethodColor(method);

        logger.info("{}{}{} {}",
                methodColor,
                method,
                RESET,
                request.getRequestURI());

        filterChain.doFilter(request,response);

        long timeTaken = System.currentTimeMillis() - start;

        int status = response.getStatus();

        logger.info("{}{}{}, Time Taken: {} ms",
                getStatusColor(status),
                status,
                RESET,
                timeTaken);
    }

    private String getMethodColor(String method){

        return switch (method){
            case "GET" -> GREEN;
            case "POST" -> BLUE;
            case "PUT" -> YELLOW;
            case "DELETE" -> RED;
            default -> CYAN;
        };
    }

    private String getStatusColor(int status){

        if (status >= 200 && status < 300){
            return GREEN;
        } else if (status >= 400 && status < 500){
            return YELLOW;
        } else if (status >= 500){
            return RED;
        }

        return CYAN;
    }
}
