package com.pedro.glic.dto;

import com.pedro.glic.enums.DiabetesType;

import java.time.LocalDate;
import java.util.List;

public record UserResponseDTO (
        Long id,
        String name,
        String email,
        LocalDate birthday,
        Integer age,
        LocalDate diagnostic,
        String timeSinceDiagnosis,
        String phone,
        Integer carbCounting,
        DiabetesType diabetesType,
        List<InsulinResponseDTO> insulins
){
}
