package com.chandankrr.scm.configuration;

import com.chandankrr.scm.entity.Providers;
import com.chandankrr.scm.entity.User;
import com.chandankrr.scm.helpers.AppConstants;
import com.chandankrr.scm.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        logger.info("Authentication success");

        DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();

        String email = Objects.requireNonNull(user.getAttribute("email")).toString();
        String name = Objects.requireNonNull(user.getAttribute("name")).toString();
        String picture = Objects.requireNonNull(user.getAttribute("picture")).toString();

        // save user data to database
        User user1 = User.builder()
                .userId(UUID.randomUUID().toString())
                .name(name)
                .email(email)
                .password("password")
                .about("This account is created using google auth...")
                .profilePic(picture)
                .enabled(true)
                .emailVerified(true)
                .provider(Providers.GOOGLE)
                .providerUserId(user.getName())
                .roles(List.of(AppConstants.ROLE_USER))
                .build();

        Optional<User> user2 = userRepository.findByEmail(email);
        if (user2.isEmpty()) {
            userRepository.save(user1);
            logger.info("User saved");
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/dashboard");
    }
}