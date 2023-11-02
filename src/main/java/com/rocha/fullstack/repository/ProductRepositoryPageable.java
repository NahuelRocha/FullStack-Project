package com.rocha.fullstack.repository;

import com.rocha.fullstack.models.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepositoryPageable extends PagingAndSortingRepository<Product,Long> {


}
