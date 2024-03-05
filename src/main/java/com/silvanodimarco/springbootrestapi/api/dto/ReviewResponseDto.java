package com.silvanodimarco.springbootrestapi.api.dto;

public record ReviewResponseDto(
    Integer id,
    String content,
    Integer rating,
    Boolean active
) {
}
