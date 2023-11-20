package com.rocha.fullstack.service;

import com.rocha.fullstack.models.MainProduct;
import com.rocha.fullstack.models.Product;
import com.rocha.fullstack.utils.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ProductService {

    Boolean createProduct(Product product);

    List<Product> getProducts(int quantity);

    Product getProductView(Long id);

    Page<Product> getAllProducts(Pageable pageable);

    Page<Product> getAll(Pageable pageable);

    Page<Product> getByCategory(Category category, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);




}
