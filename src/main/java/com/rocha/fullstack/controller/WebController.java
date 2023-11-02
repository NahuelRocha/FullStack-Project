package com.rocha.fullstack.controller;

import com.rocha.fullstack.service.serviceImpl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final UserServiceImpl userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/navbar")
    public String navbar(){ return "navbar";}

    @GetMapping("/footer")
    public String footer(){ return "footer";}

    @GetMapping("/shop")
    public String shop(){ return "shop";}

    @GetMapping("/product")
    public String product(){ return "product";}

}
