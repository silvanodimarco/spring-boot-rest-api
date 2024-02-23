package com.silvanodimarco.springbootrestapi.api.dto;

public record ProductWithBrandResponseDto(
    Integer id,
    String name,
    BrandResponseDto brand
) {}
