package com.pedro.glic.dto;

import com.pedro.glic.entity.ApplicationLog;
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
    public static ApplicationLogResponseDTO toDTO(ApplicationLog applicationLog) {
        return new ApplicationLogResponseDTO(
                applicationLog.getId(),
                applicationLog.getAppliedUnits(),
                applicationLog.getConsumedCarbs(),
                applicationLog.getApplicationTime(),
                applicationLog.calculateSuggestedDose(),
                InsulinResponseDTO.toDTO(applicationLog.getInsulin())
        );
    }
}
