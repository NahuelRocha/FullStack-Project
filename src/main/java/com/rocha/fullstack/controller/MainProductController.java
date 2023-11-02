package com.rocha.fullstack.controller;

import com.rocha.fullstack.models.MainProduct;
import com.rocha.fullstack.service.serviceImpl.MainProductImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/main-product")
@RequiredArgsConstructor
@Slf4j
public class MainProductController {

    private final MainProductImpl mainProductService;

    @PostMapping
    public ResponseEntity<String> createMainProduct(@RequestBody MainProduct product) {

        return ResponseEntity.ok(mainProductService.createMainProduct(product));
    }

    @GetMapping("{id}")
    public String getProduct(@PathVariable Long id, Model model) {

        var findProduct = mainProductService.getMainProductById(id);

        model.addAttribute("product" , findProduct);

        log.info("product {}" , findProduct);
        return "product-view";
    }
}
