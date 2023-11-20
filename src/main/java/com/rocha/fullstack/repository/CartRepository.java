package com.rocha.fullstack.repository;

import com.rocha.fullstack.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    List<Cart> findByUsername(String username);

    void deleteBySku(String sku);
    void deleteByCartId(String cartId);
    void deleteByUsername(String username);
}
