package com.rocha.fullstack.controller;

import com.rocha.fullstack.config.ApplicationConfig;
import com.rocha.fullstack.dto.OrderDetailsResponse;
import com.rocha.fullstack.dto.OrderDto;
import com.rocha.fullstack.dto.ProductWithCart;
import com.rocha.fullstack.models.Cart;
import com.rocha.fullstack.models.Product;
import com.rocha.fullstack.models.User;
import com.rocha.fullstack.repository.CartRepository;
import com.rocha.fullstack.repository.ProductRepository;
import com.rocha.fullstack.repository.UserRepository;
import com.rocha.fullstack.service.serviceImpl.ProductServiceImpl;
import com.rocha.fullstack.utils.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductServiceImpl productService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final ApplicationConfig applicationConfig;

    @PostMapping("/create")
    public ResponseEntity<Boolean> createProduct(@RequestBody Product product) {

        return ResponseEntity.ok(productService.createProduct(product));
    }

    @GetMapping("{id}")
    public String getProductView(@PathVariable("id") Long id, Model model) {

        var viewProduct = productService.getProductView(id);

        model.addAttribute("productView", viewProduct);

        log.info("product {}", viewProduct);
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
        var end = Math.min(currentPage + 5, totalPage);

        if (totalPage > 0) {
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = start; i <= end; i++) {
                pageNumbers.add(i);
            }
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "get-all";
    }

    @GetMapping("/by/{category}")
    public String getProductByCategory(@PathVariable("category") String category, @PageableDefault(size = 8) Pageable pageable, Model model) {

        Pageable pageSize = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        Page<Product> page = productService.getByCategory(Category.valueOf(category), pageSize);
        model.addAttribute("page", page);
        var totalPage = page.getTotalPages();
        var currentPage = page.getNumber();

        var start = Math.max(1, currentPage);
        var end = Math.min(currentPage + 5, totalPage);

        if (totalPage > 0) {
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = start; i <= end; i++) {
                pageNumbers.add(i);
            }
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "get-all";
    }

    @GetMapping("/products-by-name")
    public String getByName(@RequestParam("name") String name, @PageableDefault(size = 8) Pageable pageable, Model model) {
        log.info("product {}", name);
        Pageable pageSize = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        Page<Product> page = productService.findByNameContainingIgnoreCase(name, pageSize);
        model.addAttribute("page", page);
        var totalPage = page.getTotalPages();
        var currentPage = page.getNumber();

        var start = Math.max(1, currentPage);
        var end = Math.min(currentPage + 5, totalPage);

        if (totalPage > 0) {
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = start; i <= end; i++) {
                pageNumbers.add(i);
            }
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "get-all";
    }

    @GetMapping("/add-product/{sku}")
    public ResponseEntity<String> getMyShopping(@PathVariable("sku") String sku) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        Cart newCart = Cart.builder()
                .sku(sku)
                .cartId(UUID.randomUUID().toString())
                .username(username)
                .build();

        cartRepository.save(newCart);

        UserDetails userDetails = applicationConfig.userDetailsService()
                .loadUserByUsername(username);

        if (userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority
                .getAuthority().equals("ROLE_ADMIN"))) {

            return ResponseEntity.status(HttpStatus.FOUND).header("HX-Redirect", "my-shopping").body("admin");
        } else {

            return ResponseEntity.status(HttpStatus.FOUND).header("HX-Redirect", "my-shopping").body("user");
        }
    }

    @GetMapping("/get-cart-quantity")
    @ResponseBody
    public String getCartQuantity(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        List<Cart> cart = cartRepository.findByUsername(username);

        String cartQuantity = String.valueOf(cart.size());

        model.addAttribute("cartQuantity", cartQuantity);

        log.info("cartQuantity {}", cartQuantity);

        return cartQuantity;
    }

    @GetMapping("/get-cart")
    public String getCart(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        List<Cart> cartList = cartRepository.findByUsername(username);

        List<ProductWithCart> getProducts = cartList.stream()
                .map(cart -> {
                    Product product = productRepository.findBySku(cart.getSku())
                            .orElseThrow();
                    return new ProductWithCart(product, cart.getCartId());
                }).collect(Collectors.toList());

        model.addAttribute("getProducts", getProducts);

        log.info("getProducts {}", getProducts);

        return "get-cart";
    }

    @DeleteMapping("/del-cart/{cartId}")
    @Transactional
    public ResponseEntity<String> deleteCart(@PathVariable("cartId") String cartId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        cartRepository.deleteByCartId(cartId);

        UserDetails userDetails = applicationConfig.userDetailsService()
                .loadUserByUsername(username);

        if (userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority
                .getAuthority().equals("ROLE_ADMIN"))) {

            return ResponseEntity.status(HttpStatus.FOUND).header("HX-Redirect", "my-shopping").body("admin");
        } else {

            return ResponseEntity.status(HttpStatus.FOUND).header("HX-Redirect", "my-shopping").body("user");
        }
    }

    @GetMapping("/order-details")
    public String getOrderDetails(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        List<Cart> cartList = cartRepository.findByUsername(username);

        List<Product> getProducts = cartList.stream()
                .map(cart -> productRepository.findBySku(cart.getSku()).orElseThrow())
                .toList();

        model.addAttribute("getProducts", getProducts);

        Double total = getProducts.stream()
                .mapToDouble(Product::getPrice)
                .sum();

        var orderDetail = OrderDetailsResponse.builder()
                .quantity(getProducts.size())
                .total(total)
                .build();

        model.addAttribute("orderDetail", orderDetail);

        log.info("orderDetail {}", orderDetail);

        return "order-details";
    }


}

