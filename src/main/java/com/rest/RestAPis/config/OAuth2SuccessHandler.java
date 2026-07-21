package com.rest.RestAPis.config;

import com.rest.RestAPis.dao.UserRepository;
import com.rest.RestAPis.entities.User;
import com.rest.RestAPis.helper.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        Optional<User> optionalUser = userRepository.findByEmail(email);

        User user;

        if (optionalUser.isPresent()) {

            user = optionalUser.get();

        } else {

            user = new User();
            user.setName(name);
            user.setEmail(email);

            // Random password because Google users don't use password login
            user.setPassword(
                    passwordEncoder.encode(UUID.randomUUID().toString())
            );

            // Make sure this matches your database values
            user.setRole("USER");

            userRepository.save(user);
        }

        // Generate JWT
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole()
        );

        // Redirect to React with JWT
      response.sendRedirect(
    "https://litcrit.onrender.com/oauth-success"
    + "?token=" + token
    + "&role=" + user.getRole()
    + "&email=" + user.getEmail()
    +"&id="+user.getId()
);
    }
}