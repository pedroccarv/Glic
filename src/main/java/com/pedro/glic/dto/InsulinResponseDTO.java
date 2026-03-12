package com.pedro.glic.dto;

import com.pedro.glic.entity.Insulin;
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
    public static InsulinResponseDTO toDTO(Insulin insulin) {
        return new InsulinResponseDTO(
                insulin.getId(),
                insulin.getName(),
                insulin.getUnits(),
                insulin.getPrice(),
                insulin.getQuantity(),
                insulin.getOpeningDate(),
                insulin.getPurchaseDate(),
                insulin.getDescription(),
                insulin.getType(),
                insulin.getFormat()
        );
    }
}
