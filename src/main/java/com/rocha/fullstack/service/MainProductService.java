package com.rocha.fullstack.service;

import com.rocha.fullstack.models.MainProduct;

public interface MainProductService {

    String createMainProduct(MainProduct mainProduct);

    MainProduct getMainProductById(Long id);
}
