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
}
