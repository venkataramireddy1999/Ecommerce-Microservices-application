package com.nonsysncbobbal.microservices.product_service.service;

import com.nonsysncbobbal.microservices.product_service.dto.ProductDTO;
import com.nonsysncbobbal.microservices.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDTO createProduct(ProductDTO productDTO) {
        return new ProductDTO("Iphone 17", "An apple brand new smartphone", 800.00);
    }
}
