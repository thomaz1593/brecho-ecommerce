package com.thomazsilva.ecommerce.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

import com.thomazsilva.ecommerce.dto.ProductRequestDTO;
import com.thomazsilva.ecommerce.dto.ProductResponseDTO;
import com.thomazsilva.ecommerce.model.Product;
import com.thomazsilva.ecommerce.service.ProductService;

import jakarta.validation.Valid;

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

    // ============================================================
    // GET: LISTAR COM PAGINAÇÃO + FILTROS
    // ============================================================
    // @GetMapping
    // public ResponseEntity<List<Product>> getProducts(
    //     @RequestParam(required = false) String name,
    //     @RequestParam(required = false) BigDecimal minPrice,
    //     @RequestParam(required = false) BigDecimal maxPrice) {
    //         List<Product> products = productService.filterByNameAndPrice(name, minPrice, maxPrice);
    //         if (products.isEmpty()) return ResponseEntity.noContent().build();
    //         return ResponseEntity.ok(products);
    // }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> getFilteredAndPaginatedProducts(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) BigDecimal minPrice,
        @RequestParam(required = false) BigDecimal maxPrice,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductResponseDTO> results = productService.filterByNameAndPricePaginated(
            name, minPrice, maxPrice, pageable).map(p -> p.toDTO());
        return ResponseEntity.ok(results);
    }

    // ============================================================
    // POST: CRIAR PRODUTO
    // ============================================================
    // @PostMapping
    // public ResponseEntity<Product> createProduct(@RequestBody Product product) {
    //     Product createdProduct = productService.newProduct(product);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    // }
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO dto) {
        var product = productService.createProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(product.toDTO());
    }

    // ============================================================
    // GET: BUSCAR POR ID
    // ============================================================
    // @GetMapping("/{id}")
    // public ResponseEntity<Product> getProductById(@PathVariable Long id) {
    //     Product product = productService.findById(id);
    //     return ResponseEntity.ok(product);
    // }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id).toDTO());
    }

    // ============================================================
    // PUT: ATUALIZAR PRODUTO
    // ============================================================
    // @PutMapping("/{id}")
    // public ResponseEntity<Product> updateProduct(@PathVariable Long id,
    //     @RequestBody Product product) {
    //         Product updatedProduct = productService.updateProduct(id, product);
    //         return ResponseEntity.ok(updatedProduct);
    // }
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id,
        @Valid @RequestBody ProductRequestDTO dto) {
            var updated = productService.updateProduct(id, dto);
            return ResponseEntity.ok(updated.toDTO());
    }

    // ============================================================
    // DELETE
    // ============================================================
    // @DeleteMapping("/{id}")
    // public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
    //     productService.deleteProduct(id);
    //     return ResponseEntity.ok("Produto excluído com sucesso.");
    // }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
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
