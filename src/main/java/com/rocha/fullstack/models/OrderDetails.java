package com.rocha.fullstack.models;

import com.rocha.fullstack.utils.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "app_order_details")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String transaction;
    private String username;
    private Double amount;
    private String date;
    @Enumerated(EnumType.STRING)
    private Status status;
}
