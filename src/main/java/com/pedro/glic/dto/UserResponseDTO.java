package com.pedro.glic.dto;

import com.pedro.glic.entity.User;
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
        List<InsulinResponseDTO> insulins,
        List<ApplicationLogResponseDTO> applicationLog
){
    public static UserResponseDTO toDTO(User user){
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getBirthday(),
                user.getAge(),
                user.getDiagnostic(),
                user.getTimeSinceDiagnosis(),
                user.getPhone(),
                user.getCarbCounting(),
                user.getDiabetesType(),
                user.getInsulins().stream().map(InsulinResponseDTO::toDTO).toList(),
                user.getApplicationLogs().stream().map(ApplicationLogResponseDTO::toDTO).toList()
        );
    }
}
