package com.thomazsilva.ecommerce.dto;

import java.math.BigDecimal;

public record ProductResponseDTO(
    Long id,
    String name,
    BigDecimal price,
    String description,
    String imageUrl
) {}
