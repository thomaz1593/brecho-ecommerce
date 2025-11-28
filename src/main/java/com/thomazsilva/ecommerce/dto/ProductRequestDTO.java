package com.thomazsilva.ecommerce.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductRequestDTO(
    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 a 100 caracteres")
    String name,

    @NotNull(message = "O preço é obrigatório")
    @Positive(message = "O preço deve ser maior que zero")
    BigDecimal price,

    @Size(max = 2000, message = "Descrição pode ter até 2000 caracteres")
    String description,

    @Size(max = 255, message = "URL pode ter até 255 caracteres")
    String imageUrl
) {}
