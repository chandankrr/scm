package com.chandankrr.scm.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Objects;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication) {

        if(authentication instanceof OAuth2AuthenticationToken oAuth2AuthenticationToken) {
            String clintId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

            String username = "";

            if(clintId.equalsIgnoreCase("google")) {
                username = Objects.requireNonNull(oAuth2User.getAttribute("email")).toString();
            } else if(clintId.equalsIgnoreCase("github")) {
                username = oAuth2User.getAttribute("email") != null ?
                        Objects.requireNonNull(oAuth2User.getAttribute("email")).toString() :
                        Objects.requireNonNull(oAuth2User.getAttribute("login")).toString() + "@gmail.com";
            }

            return username;
        } else {
            return authentication.getName();
        }
    }
}

