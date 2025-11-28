package com.thomazsilva.ecommerce.controller;

import java.math.BigDecimal;

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

    // ============================================================
    // GET: LISTAR COM PAGINAÇÃO + FILTROS
    // ============================================================
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
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO dto) {
        var product = productService.createProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(product.toDTO());
    }

    // ============================================================
    // GET: BUSCAR POR ID
    // ============================================================
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id).toDTO());
    }

    // ============================================================
    // PUT: ATUALIZAR PRODUTO
    // ============================================================
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id,
        @Valid @RequestBody ProductRequestDTO dto) {
            var updated = productService.updateProduct(id, dto);
            return ResponseEntity.ok(updated.toDTO());
    }

    // ============================================================
    // DELETE
    // ============================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // ============================================================
    // GET: BUSCAR POR COM FILTROS
    // ============================================================
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

    // ============================================================
    // GET: PAGINAÇÃO
    // ============================================================
    @GetMapping("/paginated")
    public ResponseEntity<Page<Product>> searchProductsPaginated(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
            Page<Product> paginated = productService.getAllProductsPaginated(page, size);
            return ResponseEntity.ok(paginated);    
    }
}
