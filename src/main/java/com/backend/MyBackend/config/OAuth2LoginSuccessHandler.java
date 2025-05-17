package com.backend.MyBackend.config;

import com.backend.MyBackend.modal.User;
import com.backend.MyBackend.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    public OAuth2LoginSuccessHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println(oAuth2User);
        String username = oAuth2User.getAttribute("login");
        System.out.println("email ::: " + username);

        // Check if user exists
        Optional<User> existingUser = Optional.ofNullable(userRepository.findByUsername(username));

        User user;
        if (existingUser.isPresent()) {
            user = existingUser.get();
        } else {
            // Register new user
            user = new User();
            user.setUsername(oAuth2User.getAttribute("login")); // or name/email
            user.setRole("CUSTOMER"); // Default role
            userRepository.save(user);
        }

        // Generate your JWT
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
        System.out.println("token ::: " + token);

        // Send JWT to frontend (redirect with token, or return as JSON)
        response.sendRedirect("http://localhost:5173/dinemaster-ui/oauth-success?token=" + token);
    }
}
