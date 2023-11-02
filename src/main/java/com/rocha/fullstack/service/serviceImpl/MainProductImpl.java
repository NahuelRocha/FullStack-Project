package com.rocha.fullstack.service.serviceImpl;

import com.rocha.fullstack.exceptions.MainProductNotFoundException;
import com.rocha.fullstack.models.MainProduct;
import com.rocha.fullstack.repository.MainProductRepository;
import com.rocha.fullstack.service.MainProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainProductImpl implements MainProductService {

    private final MainProductRepository mainProductRepository;

    @Override
    public String createMainProduct(MainProduct mainProduct) {

        var newMainProduct = MainProduct.builder()
                .name(mainProduct.getName())
                .price(mainProduct.getPrice())
                .imgUrl(mainProduct.getImgUrl())
                .build();

        mainProductRepository.save(newMainProduct);

        return "SUCCESS";
    }

    @Override
    public MainProduct getMainProductById(Long id) {

        var findProduct = mainProductRepository.findById(id)
                .orElseThrow(()-> new MainProductNotFoundException("Product not found with id: " + id));

        log.info("product {}" , findProduct);

        return findProduct;
    }
}
