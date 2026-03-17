package com.pedro.glic.dto;

public record AuthResponseDTO (
        String token,
        Long userId,
        String name,
        String email
) {
}
