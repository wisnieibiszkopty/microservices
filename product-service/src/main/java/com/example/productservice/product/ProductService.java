package com.example.productservice.product;

import com.example.productservice.product.dtos.ProductCategory;
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

    public Product createProduct(Product product) {
        var exists = productRepository.existsByName(product.getName());
        if(exists){
            throw new ProductAlreadyExistsException("Produkt już istnieje");
        }

        return productRepository.save(product);
    }

    public List<ProductResponse> getAllProducts() {
        log.info("Pobieranie wszystkich produktów");
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse getProductById(Long id) {
        log.info("Pobieranie produktu o ID: {}", id);
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produkt o ID " + id + " nie został znaleziony"));
        return mapToResponse(product);
    }

    public List<ProductResponse> searchProducts(String name) {
        log.info("Wyszukiwanie produktów o nazwie: {}", name);
        return productRepository.findByNameContainingIgnoreCase(name).stream().map(this::mapToResponse).collect(Collectors.toList());
    }
    public List<ProductResponse> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<ProductResponse> getProductByCategory(ProductCategory category){
        var products = productRepository.findByCategory(category);
        return products.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public ProductResponse updateProduct(Long id, Product request) {
        var product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Produkt o ID " + id + " nie został znaleziony"));
        productRepository.save(product);
        return this.mapToResponse(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
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

}
