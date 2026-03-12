package com.pedro.glic.dto;

import com.pedro.glic.entity.Insulin;

import java.time.LocalDateTime;

public record ApplicationLogResponseDTO(
        Long id,
        Integer appliedUnits,
        Double consumedCarbs,
        LocalDateTime applicationTime,
        Double suggestedDose,
        InsulinResponseDTO insulin
) {
}
