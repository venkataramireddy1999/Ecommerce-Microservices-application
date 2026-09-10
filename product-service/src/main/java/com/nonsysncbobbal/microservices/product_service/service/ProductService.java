package com.nonsysncbobbal.microservices.product_service.service;

import com.nonsysncbobbal.microservices.product_service.dto.ProductDTO;
import com.nonsysncbobbal.microservices.product_service.model.Product;
import com.nonsysncbobbal.microservices.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = Product.builder()
                .name(productDTO.name())
                .description(productDTO.description())
                .price(productDTO.price())
                .build();
        productRepository.save(product);
        log.info("Product created: {}", product.getName());
        return new ProductDTO(product.getName(), product.getDescription(), product.getPrice());
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> new ProductDTO(product.getName(),
                        product.getDescription(),
                        product.getPrice()))
                .toList();
    }
}
