package com.rocha.fullstack.controller;

import com.rocha.fullstack.models.Cart;
import com.rocha.fullstack.models.Order;
import com.rocha.fullstack.models.Product;
import com.rocha.fullstack.models.Request;
import com.rocha.fullstack.repository.CartRepository;
import com.rocha.fullstack.repository.OrderRepository;
import com.rocha.fullstack.repository.ProductRepository;
import com.rocha.fullstack.service.serviceImpl.UserServiceImpl;
import com.rocha.fullstack.utils.Status;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserServiceImpl userService;
    @Value("${stripe.api.publicKey}")
    private String publicKey;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/order")
    public String order(Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String email = auth.getName();

        List<Cart> cartList = cartRepository.findByUsername(email);

        List<Product> getProducts = cartList.stream()
                .map(cart -> productRepository.findBySku(cart.getSku()).orElseThrow())
                .toList();

        Long amount = (long) getProducts.stream()
                .mapToDouble(Product::getPrice)
                .sum();

        model.addAttribute("amount", amount);
        model.addAttribute("email",email);
        model.addAttribute("request" , new Request(amount,email));

        return "order";
    }

    @GetMapping("/order-success")
    @Transactional
    private String orderSuccess(Model model){


        return "order-success";
    }

    @PostMapping("/order")
    @Transactional
    public String showCard(@ModelAttribute @Valid Request request, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()){

            return "order";
        }


        model.addAttribute("publicKey", publicKey);
        model.addAttribute("amount", request.getAmount());
        model.addAttribute("email", request.getEmail());

        return "checkout";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }
    @GetMapping("/register")
    public String register() {

        return "register";
    }

    @PostMapping("/register")
    public String registerSave() {

        return "register";
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
