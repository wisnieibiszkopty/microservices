package com.example.productservice.product.dtos;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private ProductCategory category;
    private Integer stockQuantity;
}
