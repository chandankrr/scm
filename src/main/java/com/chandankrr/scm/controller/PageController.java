package com.chandankrr.scm.controller;

import com.chandankrr.scm.entity.User;
import com.chandankrr.scm.form.UserForm;
import com.chandankrr.scm.helpers.Message;
import com.chandankrr.scm.helpers.MessageType;
import com.chandankrr.scm.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PageController {

    private final UserService userService;

    @RequestMapping("/")
    public String index() {
        log.info("index page handler");
        return "redirect:/home";
    }

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
        log.info("Services page handler");
        return "services";
    }

    @RequestMapping("/contact")
    public String contact() {
        log.info("Contact page handler");
        return "contact";
    }

    @RequestMapping("/login")
    public String login() {
        log.info("Login page handler");
        return "login";
    }

    @RequestMapping("/signup")
    public String signup(Model model) {
        log.info("Signup page handler");

        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);

        return "signup";
    }

    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult,
            HttpSession session) {
        log.info("Processing registration");

        // validate useForm
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        User user = User.builder()
                .name(userForm.getName())
                .email(userForm.getEmail())
                .password(userForm.getPassword())
                .phoneNumber(userForm.getPhoneNumber())
                .about(userForm.getAbout())
                .profilePic(
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b5/Windows_10_Default_Profile_Picture.svg/2048px-Windows_10_Default_Profile_Picture.svg.png")
                .build();

        userService.saveUser(user);

        Message message = Message.builder()
                .content("Registration Successful")
                .messageType(MessageType.green)
                .build();

        session.setAttribute("message", message);

        return "redirect:/signup";
    }
}
