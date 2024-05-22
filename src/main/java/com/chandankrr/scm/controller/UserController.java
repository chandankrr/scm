package com.chandankrr.scm.controller;

import com.chandankrr.scm.entity.User;
import com.chandankrr.scm.exception.ResourceNotFoundException;
import com.chandankrr.scm.helpers.Helper;
import com.chandankrr.scm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String userDashboard() {
        return "user/dashboard";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String userProfile(Model model, Authentication authentication) throws ResourceNotFoundException {
        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(username);

        logger.info("User logged in: {}, {}", user.getName(), user.getEmail());

        model.addAttribute("loggedInUser", user);
        return "user/profile";
    }

}
