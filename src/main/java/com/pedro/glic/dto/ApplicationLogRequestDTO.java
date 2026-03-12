package com.pedro.glic.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

public record ApplicationLogRequestDTO(
        @NotNull @PositiveOrZero Integer appliedUnits,
        @NotNull @PositiveOrZero LocalDateTime applicationTime,
        Double consumedCarbs,
        @NotNull Long insulinId,
        @NotNull Long userId
) {
}
