package com.nonsysncbobbal.microservices.product_service.dto;

import java.math.BigDecimal;

public record ProductDTO(String name, String description, BigDecimal price) {
}
