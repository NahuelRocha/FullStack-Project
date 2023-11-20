package com.rocha.fullstack.controller;

import com.rocha.fullstack.models.User;
import com.rocha.fullstack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserRepository repository;

    @GetMapping("/user")
    public String getUserPanel(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = repository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        model.addAttribute("user", user);

        return "user";
    }

    @GetMapping("/user-details")
    public String getUserDetails(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = repository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        model.addAttribute("user", user);

        return "user-details";
    }

    @GetMapping("/my-shopping")
    public String getMyShopping(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = repository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));


        model.addAttribute("user", user);

        return "my-shopping";
    }


}
