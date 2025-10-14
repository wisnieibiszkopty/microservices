package com.example.productservice.product;

import com.example.productservice.product.dtos.ProductCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100,
            message = "Name must have between 2 and 100 characters")
    @Column(nullable = false)
    private String name;

    @NotNull
    @Size(min = 1, max = 1000)
    private String description;

    @NotNull
    @DecimalMin(value = "0.01")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @NotNull
    @Min(0)
    private Integer stockQuantity;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
