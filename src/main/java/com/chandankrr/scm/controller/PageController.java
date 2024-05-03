package com.chandankrr.scm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class PageController {

    @RequestMapping("/home")
    public String home() {
        log.info("Home page handler");
        return "home";
    }

    @RequestMapping("/about")
    public String about() {
        log.info("About page handler");
        return "about";
    }

    @RequestMapping("/services")
    public String services() {
        log.info("services page handler");
        return "services";
    }

    @RequestMapping("/contact")
    public String contact() {
        log.info("contact page handler");
        return "contact";
    }

    @RequestMapping("/login")
    public String login() {
        log.info("login page handler");
        return "login";
    }

    @RequestMapping("/signup")
    public String signup() {
        log.info("signup page handler");
        return "signup";
    }
}
