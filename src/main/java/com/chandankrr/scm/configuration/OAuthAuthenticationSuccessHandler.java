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
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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

        // identify provider
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

        String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        logger.info(authorizedClientRegistrationId);

        DefaultOAuth2User oAuthUser = (DefaultOAuth2User) authentication.getPrincipal();

//        oAuthUser.getAttributes().forEach((key, value) -> {
//            logger.info("{}: {}", key, value);
//        });

        // default/manual generated user details
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setRoles(List.of(AppConstants.ROLE_USER));
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setPassword("password");

        // based on provider: saving the details in database
        if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {
            user.setEmail(Objects.requireNonNull(oAuthUser.getAttribute("email")).toString());
            user.setProfilePic(Objects.requireNonNull(oAuthUser.getAttribute("picture")).toString());
            user.setName(Objects.requireNonNull(oAuthUser.getAttribute("name")).toString());
            user.setProviderUserId(oAuthUser.getName());
            user.setProvider(Providers.GOOGLE);
            user.setAbout("This account is created using google auth...");

        } else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
            user.setEmail(oAuthUser.getAttribute("email") != null ?
                    Objects.requireNonNull(oAuthUser.getAttribute("email")).toString() :
                    Objects.requireNonNull(oAuthUser.getAttribute("login")).toString() + "@gmail.com");
            user.setProfilePic(Objects.requireNonNull(oAuthUser.getAttribute("avatar_url")).toString());
            user.setName(oAuthUser.getAttribute("name") != null ?
                    Objects.requireNonNull(oAuthUser.getAttribute("name")).toString() :
                    Objects.requireNonNull(oAuthUser.getAttribute("login")).toString());
            user.setProviderUserId(oAuthUser.getName());
            user.setProvider(Providers.GITHUB);
            user.setAbout("This account is created using github auth...");
        } else {
            logger.info("OAuthAuthenticationSuccessHandler: unknown oauth2 client registration id");
        }

        Optional<User> user1 = userRepository.findByEmail(user.getEmail());
        if (user1.isEmpty()) {
            userRepository.save(user);
            logger.info("User saved");
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/dashboard");
    }
}