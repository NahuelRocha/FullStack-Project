package com.rocha.fullstack.repository;

import com.rocha.fullstack.models.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails,Long> {

    List<OrderDetails> findByUsername(String username);
}
