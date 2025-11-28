package com.thomazsilva.ecommerce.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thomazsilva.ecommerce.dto.ProductRequestDTO;
import com.thomazsilva.ecommerce.enums.FilterType;
import com.thomazsilva.ecommerce.exception.ProductNotFoundException;
import com.thomazsilva.ecommerce.model.Product;
import com.thomazsilva.ecommerce.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private static final BigDecimal MAX_PRICE = new BigDecimal("999999999999999");
    private static final BigDecimal MIN_PRICE = BigDecimal.ZERO;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product newProduct(Product product) {
        return productRepository.save(product);
    }

    // -----------------------
    // READ
    // -----------------------
    public Product findById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product updateProduct(Long id, Product product) {
        Product existingProduct = findById(id);
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setImageUrl(product.getImageUrl());
        return productRepository.save(existingProduct);
    }

    // -----------------------
    // DELETE
    // -----------------------
    public void deleteProduct(Long id) {
        Product existingProduct = findById(id);
        productRepository.deleteById(existingProduct.getId());
    }

    private FilterType detectFilterType(String name, BigDecimal minPrice, BigDecimal maxPrice) {
        boolean hasName = name != null && !name.isBlank();
        boolean hasMin = minPrice != null;
        boolean hasMax = maxPrice != null;

        if (hasName && hasMin && hasMax) return FilterType.NAME_MIN_MAX;
        if (hasName && hasMin) return FilterType.NAME_MIN;
        if (hasName && hasMax) return FilterType.NAME_MAX;
        if (hasName) return FilterType.NAME_ONLY;
        if (hasMin || hasMax) return FilterType.MIN_MAX;

        return FilterType.NONE;
    }

    public List<Product> filterByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> filterByPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        if (minPrice == null) return productRepository.findByPriceLessThanEqual(maxPrice);
        if (maxPrice == null) return productRepository.findByPriceGreaterThanEqual(minPrice);
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    // public List<Product> filterByNameAndPrice(String name, BigDecimal minPrice, BigDecimal maxPrice) {
    //     if (name != null && !name.isEmpty() && minPrice != null && maxPrice != null)
    //         return productRepository.findByNameContainingIgnoreCaseAndPriceBetween(name, minPrice, maxPrice);
    //     if (name != null && !name.isEmpty()) return filterByName(name);
    //     if (minPrice != null || maxPrice != null) return filterByPrice(minPrice, maxPrice);
    //     return getAllProducts();
    // }

    public List<Product> filterByNameAndPrice(String name, BigDecimal minPrice, BigDecimal maxPrice) {
        // boolean hasName = name != null && !name.isEmpty();
        // boolean hasMin = minPrice != null;
        // boolean hasMax = maxPrice != null;

        // if (hasName && hasMin && hasMax) {
        //     return productRepository.findByNameContainingIgnoreCaseAndPriceBetween(name, minPrice, maxPrice);
        // }
        
        // if (hasName && hasMin) {
        //     return productRepository.findByNameContainingIgnoreCaseAndPriceGreaterThanEqual(name, minPrice);
        // }
        
        // if (hasName && hasMax) {
        //     return productRepository.findByNameContainingIgnoreCaseAndPriceLessThanEqual(name, maxPrice);
        // }
        
        // if (hasName) {
        //     return filterByName(name);
        // }
        
        // if (hasMin || hasMax) {
        //     return filterByPrice(minPrice, maxPrice);
        // } 
        
        // return getAllProducts();
        FilterType type = detectFilterType(name, minPrice, maxPrice);
        switch (type) {
            case NAME_MIN_MAX:
                return productRepository.findByNameContainingIgnoreCaseAndPriceBetween(
                    name, minPrice, maxPrice);
            case NAME_MIN:
                return productRepository.findByNameContainingIgnoreCaseAndPriceGreaterThanEqual(
                    name, minPrice);
            case NAME_MAX:
                return productRepository.findByNameContainingIgnoreCaseAndPriceLessThanEqual(
                    name, maxPrice);
            case NAME_ONLY:
                return productRepository.findByNameContainingIgnoreCase(name);
            case MIN_MAX:
                return filterByPrice(minPrice, maxPrice);
            case NONE:
            default:
                return getAllProducts();
        }
    }

    // public Page<Product> getProductsPaginated(
    //     String name, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
    //         boolean hasName = name != null && !name.isEmpty();
    //         boolean hasMin = minPrice != null;
    //         boolean hasMax = maxPrice != null;

    //         if (hasName && hasMin && hasMax) {
    //             return productRepository.findByNameContainingIgnoreCaseAndPriceBetween(
    //                 name, minPrice, maxPrice, pageable);
    //         } else if (hasName) {
    //             return productRepository.findByNameContainingIgnoreCase(name, pageable);
    //         } else if (hasMin || hasMax) {
    //             if (minPrice == null) return productRepository.findByPriceLessThanEqual(maxPrice, pageable);
    //             if (maxPrice == null) return productRepository.findByPriceGreaterThanEqual(minPrice, pageable);
    //             return productRepository.findByPriceBetween(minPrice, maxPrice, pageable);
    //         } else {
    //             return productRepository.findAll(pageable);
    //         }
    // }

    public Page<Product> filterByNameAndPricePaginated(
        String name, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable
    ) {
        // boolean hasName = name != null && !name.isEmpty();
        // boolean hasMin = minPrice != null;
        // boolean hasMax = maxPrice != null;

        // if (hasName && hasMin && hasMax) {
        //     return productRepository.findByNameContainingIgnoreCaseAndPriceBetween(
        //         name, minPrice, maxPrice, pageable);
        // }

        // if (hasName && hasMin) {
        //     return productRepository.findByNameContainingIgnoreCaseAndPriceGreaterThanEqual(name, minPrice, pageable);
        // }
        
        // if (hasName && hasMax) {
        //     return productRepository.findByNameContainingIgnoreCaseAndPriceLessThanEqual(name, maxPrice, pageable);
        // }
        
        // if (hasName) {
        //     return productRepository.findByNameContainingIgnoreCase(name, pageable);
        // }
        
        // if (hasMin || hasMax) {
        //     BigDecimal realMin = hasMin ? minPrice : BigDecimal.ZERO;
        //     // BigDecimal realMax = hasMax ? maxPrice : BigDecimal.valueOf(Double.MAX_VALUE);
        //     BigDecimal realMax = hasMax ? maxPrice : new BigDecimal("9999999999");
        //     return productRepository.findByPriceBetween(realMin, realMax, pageable);
        // }

        // return productRepository.findAll(pageable);
        FilterType type = detectFilterType(name, minPrice, maxPrice);
        switch (type) {
            case NAME_MIN_MAX:
                return productRepository.findByNameContainingIgnoreCaseAndPriceBetween(
                    name, minPrice, maxPrice, pageable);
            case NAME_MIN:
                return productRepository.findByNameContainingIgnoreCaseAndPriceGreaterThanEqual(
                    name, minPrice, pageable);
            case NAME_MAX:
                return productRepository.findByNameContainingIgnoreCaseAndPriceLessThanEqual(
                    name, maxPrice, pageable);
            case NAME_ONLY:
                return productRepository.findByNameContainingIgnoreCase(name, pageable);
            case MIN_MAX:
                BigDecimal realMin = (minPrice != null) ? minPrice : MIN_PRICE;
                BigDecimal realMax = (maxPrice != null) ? maxPrice : MAX_PRICE;
                return productRepository.findByPriceBetween(realMin, realMax, pageable);
            case NONE:
            default:
                return productRepository.findAll(pageable);
        }
    }

    public Page<Product> getAllProductsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    // -----------------------
    // HELPERS
    // -----------------------
    private Product fromDTO(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setDescription(dto.description());
        product.setImageUrl(dto.imageUrl());
        return product;
    }

    private void updateEntity(Product product, ProductRequestDTO dto) {
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setDescription(dto.description());
        product.setImageUrl(dto.imageUrl());
    }

    // -----------------------
    // CREATE (POST)
    // -----------------------
    public Product createProduct(ProductRequestDTO dto) {
        Product product = fromDTO(dto);
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductRequestDTO dto) {
        Product existing = findById(id);
        updateEntity(existing, dto);
        return productRepository.save(existing);
    }
}
