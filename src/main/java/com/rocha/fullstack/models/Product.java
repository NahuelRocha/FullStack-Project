package com.rocha.fullstack.models;

import com.rocha.fullstack.utils.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    private String sku;

    private String name;

    private String description;

    private Double price;

    private Long stock;

    private String maker;

    private String image;

    @Enumerated(value = EnumType.STRING)
    private Category category;
}
