package com.rocha.fullstack.models;

import com.rocha.fullstack.utils.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "app_order")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private String address;
    private String zipcode;
    private String city;
    private String email;
    private String phone;
    private String date;
    @Enumerated(EnumType.STRING)
    private Status status;

}
