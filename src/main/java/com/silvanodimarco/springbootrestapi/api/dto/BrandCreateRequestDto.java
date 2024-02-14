package com.silvanodimarco.springbootrestapi.api.dto;

import com.silvanodimarco.springbootrestapi.api.validation.BrandCreate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BrandCreateRequestDto(
    @NotBlank(groups = {BrandCreate.class}, message = "The field is required")
    @Size(max = 50, groups = {BrandCreate.class}, message = "The field must be less than 50 characters")
    String name
) {
}
