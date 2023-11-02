package com.rocha.fullstack.repository;

import com.rocha.fullstack.models.Product;
import com.rocha.fullstack.utils.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findBySku(String sku);

    Page<Product> findByCategory(Category category, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Product> findByNameStartingWithIgnoreCase(String name, Pageable pageable);


}
