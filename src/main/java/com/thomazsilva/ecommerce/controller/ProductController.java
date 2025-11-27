package com.thomazsilva.ecommerce.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thomazsilva.ecommerce.model.Product;
import com.thomazsilva.ecommerce.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // @GetMapping("/get-products")
    // public ResponseEntity<List<Product>> getAllProducts() {
    //     List<Product> products = productService.getAllProducts();
    //     return ResponseEntity.ok(products);
    // }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) BigDecimal minPrice,
        @RequestParam(required = false) BigDecimal maxPrice) {
            List<Product> products = productService.filterByNameAndPrice(name, minPrice, maxPrice);
            if (products.isEmpty()) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(products);
    }


    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.newProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
        @RequestBody Product product) {
            Product updatedProduct = productService.updateProduct(id, product);
            return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Produto excluído com sucesso.");
    }

    // @GetMapping("/product/filter")
    // public ResponseEntity<List<Product>> filterProductByName(@RequestParam String name) {
    //     List<Product> filteredProducts = productService.filterByName(name);
    //     return ResponseEntity.ok(filteredProducts);
    // }

    // @GetMapping("/product/filter/price")
    // public ResponseEntity<List<Product>> filterProductByPrice(
    //     @RequestParam(required = false) BigDecimal minPrice,
    //     @RequestParam(required = false) BigDecimal maxPrice) {
    //         List<Product> products;
    //         if (minPrice == null && maxPrice == null) {
    //             products = productService.getAllProducts();
    //         } else {
    //             products = productService.filterByPrice(minPrice, maxPrice);
    //         }
    //         return ResponseEntity.ok(products);
    // }

    // @GetMapping("/get-products/sort")
    // public ResponseEntity<Page<Product>> getAllProducts(
    //     @RequestParam(required = false) String name,
    //     @RequestParam(required = false) BigDecimal minPrice,
    //     @RequestParam(required = false) BigDecimal maxPrice,
    //     Pageable pageable
    // ) {
    //     Page<Product> page = productService.getProductsPaginated(name, minPrice, maxPrice, pageable);
    //     return ResponseEntity.ok(page);
    // }

    @GetMapping("/search")
    public ResponseEntity<Page<Product>> searchProducts(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) BigDecimal minPrice,
        @RequestParam(required = false) BigDecimal maxPrice,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> filtered = productService.filterByNameAndPricePaginated(name, minPrice, maxPrice, pageable);
        return ResponseEntity.ok(filtered);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<Product>> searchProductsPaginated(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
            Page<Product> paginated = productService.getAllProductsPaginated(page, size);
            return ResponseEntity.ok(paginated);    
    }
}
