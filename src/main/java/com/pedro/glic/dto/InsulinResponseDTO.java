package com.pedro.glic.dto;

import com.pedro.glic.enums.InsulinFormat;
import com.pedro.glic.enums.InsulinType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record InsulinResponseDTO(
        Long id,
        String name,
        Integer units,
        BigDecimal price,
        Integer quantity,
        LocalDate openingDate,
        LocalDate purchaseDate,
        String description,
        InsulinType type,
        InsulinFormat format
) {
}
