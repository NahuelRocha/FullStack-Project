package com.rocha.fullstack.controller;

import com.rocha.fullstack.config.ApplicationConfig;
import com.rocha.fullstack.dto.OrderDto;
import com.rocha.fullstack.models.*;
import com.rocha.fullstack.repository.*;
import com.rocha.fullstack.utils.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final UserRepository userRepository;

    @PostMapping("/create-order")
    @Transactional
    public ResponseEntity<String> createOrder(OrderDto order){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        orderRepository.deleteByEmail(username);

        List<Cart> cartList = cartRepository.findByUsername(username);

        List<Product> getProducts = cartList.stream()
                .map(cart -> productRepository.findBySku(cart.getSku()).orElseThrow())
                .toList();

        for(Product product : getProducts){
             if(product.getStock() >= 1){
                 product.setStock(product.getStock() - 1);
             } else {
                 return ResponseEntity.status(HttpStatus.FOUND).header("HX-Redirect", "shop").body("admin");
             }
        }

        var newOrder = Order.builder()
                .firstname(order.firstname())
                .lastname(order.lastname())
                .email(order.email())
                .address(order.address())
                .phone(order.phone())
                .city(order.city())
                .zipcode(order.zipcode())
                .date(new Date(System.currentTimeMillis()).toString())
                .status(Status.PLACED)
                .build();

        orderRepository.save(newOrder);

        Double amount = getProducts.stream()
                .mapToDouble(Product::getPrice)
                .sum();

        return ResponseEntity.status(HttpStatus.FOUND).header("HX-Redirect", "order").body("admin");

    }

    @PostMapping("/view-details")
    @Transactional
    public String orderDetails(Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        log.info("user {}", username);

        List<String> sku = cartRepository.findByUsername(username).stream()
                .map(Cart::getSku).toList();


        Double amount = sku.stream().map(s -> productRepository.findBySku(s).stream()
                .mapToDouble(Product::getPrice).sum()).reduce(Double::sum)
                .orElseThrow();

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setTransaction(UUID.randomUUID().toString());
        orderDetails.setUsername(username);
        orderDetails.setAmount(amount);
        orderDetails.setDate(new Date(System.currentTimeMillis()).toString());
        orderDetails.setStatus(Status.SUCCESS);

        orderDetailsRepository.save(orderDetails);

        cartRepository.deleteByUsername(username);

        model.addAttribute(orderDetails);

        return  "view-details";
    }

    @GetMapping("/view-details")
    public String viewDetails(Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        List<OrderDetails> orderDetails = orderDetailsRepository.findByUsername(username);

        OrderDetails order = orderDetails.get(0);

        model.addAttribute(order);

        return "view-details";
    }

    @GetMapping("/list-details")
    public String listOfOrders(Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        List<OrderDetails> orderDetails = orderDetailsRepository.findByUsername(username);

        model.addAttribute("orderDetails", orderDetails);

        return "list-details";
    }

    @GetMapping("/view-order/{id}")
    public String viewDetailsById(@PathVariable("id") Long id, Model model){

        OrderDetails orderDetails = orderDetailsRepository.findById(id)
                .orElseThrow();

        model.addAttribute("orderDetails", orderDetails);

        return "view-order";
    }
}
