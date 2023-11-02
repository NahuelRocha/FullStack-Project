package com.rocha.fullstack.controller;

import com.rocha.fullstack.models.Product;
import com.rocha.fullstack.service.serviceImpl.ProductServiceImpl;
import com.rocha.fullstack.utils.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductServiceImpl productService;

    @PostMapping("/create")
    public ResponseEntity<Boolean> createProduct(@RequestBody Product product){

        return ResponseEntity.ok(productService.createProduct(product));
    }

    @GetMapping("{id}")
    public String getProductView(@PathVariable("id") Long id, Model model) {

        var viewProduct = productService.getProductView(id);

        model.addAttribute("productView" , viewProduct);

        log.info("product {}" , viewProduct);
        return "product";
    }

    @GetMapping("/all")
    public String getAllProducts(
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "3") int size,
                                 Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productService.getAllProducts(pageable);
        model.addAttribute("products", products);
        return "all-products";
    }

    @GetMapping("/all-products")
    public String getAll(@PageableDefault(size = 8) Pageable pageable, Model model) {

        Pageable pageSize = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        Page<Product> page = productService.getAll(pageSize);
        model.addAttribute("page", page);
        var totalPage = page.getTotalPages();
        var currentPage = page.getNumber();

        var start = Math.max(1, currentPage);
        var end = Math.min(currentPage + 5 , totalPage);

        if (totalPage > 0){
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = start; i <= end ; i++) {
                pageNumbers.add(i);
            }
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "get-all";
    }
    @GetMapping("/by/{category}")
    public String getProductByCategory(@PathVariable("category") String category, @PageableDefault(size = 8) Pageable pageable, Model model) {

        Pageable pageSize = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        Page<Product> page = productService.getByCategory(Category.valueOf(category),pageSize);
        model.addAttribute("page", page);
        var totalPage = page.getTotalPages();
        var currentPage = page.getNumber();

        var start = Math.max(1, currentPage);
        var end = Math.min(currentPage + 5 , totalPage);

        if (totalPage > 0){
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = start; i <= end ; i++) {
                pageNumbers.add(i);
            }
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "get-all";
    }

    @GetMapping("/products-by-name")
    public String getByName(@RequestParam("name") String name, @PageableDefault(size = 8) Pageable pageable, Model model) {
        log.info("product {}" , name);
        Pageable pageSize = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        Page<Product> page = productService.findByNameContainingIgnoreCase(name,pageSize);
        model.addAttribute("page", page);
        var totalPage = page.getTotalPages();
        var currentPage = page.getNumber();

        var start = Math.max(1, currentPage);
        var end = Math.min(currentPage + 5 , totalPage);

        if (totalPage > 0){
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = start; i <= end ; i++) {
                pageNumbers.add(i);
            }
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "get-all";
    }


}

