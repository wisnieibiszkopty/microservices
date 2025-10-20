package com.example.productservice.product;

import com.example.productservice.product.dtos.ProductCategory;
import com.example.productservice.product.dtos.ProductRequest;
import com.example.productservice.product.dtos.ProductResponse;
import com.example.productservice.product.exceptions.ProductAlreadyExistsException;
import com.example.productservice.product.exceptions.ProductNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public Product createProduct(ProductRequest product) {
        var exists = productRepository.existsByName(product.getName());
        if(exists){
            throw new ProductAlreadyExistsException("Produkt już istnieje");
        }

        return productRepository.save(mapToProduct(product));
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse getProductById(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Produkt o ID " + id + " nie został znaleziony"));
        return mapToResponse(product);
    }

    public List<ProductResponse> searchProducts(String name) {
        return productRepository
                .findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository
                .findByPriceBetween(minPrice, maxPrice)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductByCategory(ProductCategory category){
        var products = productRepository.findByCategory(category);
        return products
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse updateProduct(Long id, Product request) {
        var product = productRepository
                .findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Produkt o ID " + id + " nie został znaleziony"));
        productRepository.save(product);
        return this.mapToResponse(product);
    }

    public void deleteProduct(Long id) {
        var product = productRepository
                .findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Produkt o ID " + id + " nie został znaleziony"));
        productRepository.delete(product);
    }

    private ProductResponse mapToResponse(Product product) {
        var response = new ProductResponse();
        response.setId(product.getId());
        response.setCategory(product.getCategory());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());
        return response;
    }

    public Product mapToProduct(ProductRequest request) {
        var product = new Product();
        product.setCategory(request.getCategory());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        return product;
    }

}
