package com.rocha.fullstack.service.serviceImpl;

import com.rocha.fullstack.exceptions.MainProductNotFoundException;
import com.rocha.fullstack.models.MainProduct;
import com.rocha.fullstack.models.Product;
import com.rocha.fullstack.repository.ProductRepository;
import com.rocha.fullstack.repository.ProductRepositoryPageable;
import com.rocha.fullstack.service.ProductService;
import com.rocha.fullstack.utils.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductRepositoryPageable productRepositoryPageable;
    @Override
    public Boolean createProduct(Product product) {

        var checkSkuAvailable = productRepository.findBySku(product.getSku());

        if (checkSkuAvailable.isPresent()){
            return false;
        } else {
            var newProduct = Product.builder()
                    .sku(product.getSku())
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .stock(product.getStock())
                    .maker(product.getMaker())
                    .category(product.getCategory())
                    .image(product.getImage())
                    .build();
            log.info("OBJETO {}", newProduct);

            productRepository.save(newProduct);

            return true;
        }
    }

    @Override
    public List<Product> getProducts(int quantity) {

        return null;

    }

    @Override
    public Product getProductView(Long id) {

        var findProduct = productRepository.findById(id)
                .orElseThrow(()-> new MainProductNotFoundException("Product not found with id: " + id));

        log.info("product {}" , findProduct);

        return findProduct;
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {

        Page<Product> list = productRepository.findAll(pageable);

        if (list.isEmpty()){
            return Page.empty();
        }
        return list;
    }

    @Override
    public Page<Product> getAll(Pageable pageable) {
        Page<Product> list = productRepositoryPageable.findAll(pageable);

        if (list.isEmpty()){
            return Page.empty();
        }
        return list;
    }

    @Override
    public Page<Product> getByCategory(Category category, Pageable pageable) {
        Page<Product> list = productRepository.findByCategory(category,pageable);

        if (list.isEmpty()){
            return Page.empty();
        }
        return list;
    }

    @Override
    public Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable) {
        log.info("product {}" , name);
        Page<Product> list = productRepository.findByNameContainingIgnoreCase(name,pageable);
        log.info("product {}" , list);
        if (list.isEmpty()){
            return Page.empty();
        }
        return list;
    }
}
