package com.thomazsilva.ecommerce.repository;

import com.thomazsilva.ecommerce.model.Product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigDecimal;


public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    List<Product> findByPriceLessThanEqual(BigDecimal maxPrice);

    List<Product> findByPriceGreaterThanEqual(BigDecimal minPrice);

    List<Product> findByNameContainingIgnoreCaseAndPriceBetween(String name, BigDecimal minPrice, BigDecimal maxPrice);

    List<Product> findByNameContainingIgnoreCaseAndPriceGreaterThanEqual(String name, BigDecimal minPrice);

    List<Product> findByNameContainingIgnoreCaseAndPriceLessThanEqual(String name, BigDecimal maxPrice);

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Page<Product> findByPriceGreaterThanEqual(BigDecimal minPrice, Pageable pageable);

    Page<Product> findByPriceLessThanEqual(BigDecimal maxPrice, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCaseAndPriceBetween(String name, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCaseAndPriceGreaterThanEqual(String name, BigDecimal minPrice, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCaseAndPriceLessThanEqual(String name, BigDecimal maxPrice, Pageable pageable);
}
