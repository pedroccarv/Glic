package com.pedro.glic.dto;

import com.pedro.glic.enums.InsulinFormat;
import com.pedro.glic.enums.InsulinType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record InsulinRequestDTO (
       @NotBlank String name,
       @Positive Integer units,
       @PositiveOrZero BigDecimal price,
       @Positive Integer quantity,
       LocalDate openingDate,
       @PastOrPresent LocalDate purchaseDate,
       String description,
       @NotNull InsulinType type,
       @NotNull InsulinFormat format
) {
}
