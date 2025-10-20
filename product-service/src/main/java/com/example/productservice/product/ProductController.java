package com.example.productservice.product;

import com.example.productservice.product.dtos.ProductCategory;
import com.example.productservice.product.dtos.ProductRequest;
import com.example.productservice.product.dtos.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
    })
    @Tag(name = "get", description = "Retrieve all products")
    @Operation(summary = "Retrieve all products", description = "Return list of ProductResponse objects")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        log.info("GET /api/v1/products - Pobieranie wszystkich produktów");
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductResponse.class)
                            )
                    })
    })
    @Tag(name = "get", description = "Retrieve product with given id")
    @Operation(summary = "Retrieve product with given id", description = "Return single ProductResponse")
    public ResponseEntity<ProductResponse> getProductById(
            @Parameter(description = "ID of product to be retrieved", required = true)
            @PathVariable Long id
    ) {
        log.info("GET /api/v1/products/{} - Pobieranie produktu", id);
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/category/{category}")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductResponse.class)
                            )
                    })
    })
    @Tag(name = "get", description = "Receive products with given category")
    @Operation(summary = "Retrieve product with given category", description = "Return list of ProductResponse objects")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(
            @Parameter(description = "Category of products", required = true)
            @PathVariable ProductCategory category){
        log.info("GET /api/v1/products/{} - Szukanie po kategorii", category);
        var products = productService.getProductByCategory(category);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductResponse.class)
                            )
                    })
    })
    @Tag(name = "get", description = "Receive products with given name")
    @Operation(summary = "Retrieve product with given name", description = "Return list of ProductResponse objects")
    public ResponseEntity<List<ProductResponse>> searchProducts(
            @Parameter(description = "Name of product", required = true)
            @RequestParam String name
    ) {
        log.info("GET /api/v1/products/{} - Szukanie po nazwie", name);
        return ResponseEntity.ok(productService.searchProducts(name));
    }

    @GetMapping("/price-range")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class)
                    )
            })
    })
    @Tag(name = "get", description = "Receive products with given price range")
    @Operation(summary = "Retrieve product with given price range", description = "Return list of ProductResponse objects")
    public ResponseEntity<List<ProductResponse>> getProductsByPriceRange(
            @Parameter(description = "Minimal price of product", required = true)
            @RequestParam BigDecimal minPrice,
            @Parameter(description = "Maximal price of product", required = true)
            @RequestParam BigDecimal maxPrice) {
        log.info("GET /api/v1/products/{}-{} - Szukanie po cenie", minPrice, maxPrice);
        return ResponseEntity.ok(productService.getProductsByPriceRange(minPrice, maxPrice));
    }

    @PostMapping
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400")
    })
    @Tag(name = "post", description = "Create product")
    @Operation(summary = "Create product", description = "Return single ProductResponse")
    public ResponseEntity<Product> createProduct(
            @Parameter(description = "Product data", required = true)
            @Valid @RequestBody ProductRequest request) {
        log.info("POST /api/v1/products - Tworzenie produktu");
        var product = productService.createProduct(request);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404")
    })
    @Tag(name = "put", description = "Update product with given id")
    @Operation(summary = "Update product with given id", description = "Return single ProductResponse")
    public ResponseEntity<ProductResponse> updateProduct(
            @Parameter(description = "Id of product which should be updated", required = true)
            @PathVariable Long id,
            @Parameter(description = "Product data to update", required = true)
            @Valid @RequestBody Product request) {
        log.info("UPDATE /api/v1/products/{} - Modyfikacjia produktu", id);
        var product = productService.updateProduct(id, request);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404")
    })
    @Tag(name = "delete", description = "Delete product with given id")
    @Operation(summary = "Delete product with given id", description = "Return no content")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "Id of product which sholud be deleted", required = true)
            @PathVariable Long id) {
        log.info("DELETE /api/v1/products/{} - Usuwanie produktu", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
