package com.github.archturion64.thirdeye.controllers;

import com.github.archturion64.thirdeye.domains.Cve;
import com.github.archturion64.thirdeye.domains.Device;
import com.github.archturion64.thirdeye.domains.User;
import com.github.archturion64.thirdeye.repositories.UserRepository;
import com.github.archturion64.thirdeye.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class AppController {

    private final UserService userService;

    @GetMapping("")
    public String showHomePage(){
        return "index";
    }

    @GetMapping("/register")
    public String showSingUpForm(Model model){
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegistration(@ModelAttribute(value = "user") User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userService.save(user);
        return "register_success";
    }
}
