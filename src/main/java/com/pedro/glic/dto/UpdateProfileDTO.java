package com.pedro.glic.dto;

import com.pedro.glic.enums.DiabetesType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record UpdateProfileDTO (
        @NotNull @PastOrPresent LocalDate birthday,
        @NotNull @PastOrPresent LocalDate diagnostic,
        @NotNull String phone,
        @PositiveOrZero Integer carbCounting,
        @NotNull DiabetesType diabetesType
) {
}
