package com.rocha.fullstack.repository;

import com.rocha.fullstack.models.MainProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainProductRepository extends JpaRepository<MainProduct, Long> {
}
