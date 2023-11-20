package com.rocha.fullstack.repository;

import com.rocha.fullstack.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    void deleteByEmail(String email);

    Optional<Order> findByEmail(String email);
}
