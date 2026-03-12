package com.pedro.glic.dto;

import com.pedro.glic.enums.DiabetesType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDate;

public record UserRequestDTO(
       @NotBlank String name,
       @NotBlank String email,
       @NotBlank String password,
       @NotNull LocalDate birthday,
       @NotNull LocalDate diagnostic,
       @NotBlank String phone,
       @PositiveOrZero Integer carbCounting,
       @NotNull DiabetesType diabetesType
) {
}
