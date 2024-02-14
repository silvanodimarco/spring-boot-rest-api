package com.silvanodimarco.springbootrestapi.api.dto;


import com.silvanodimarco.springbootrestapi.api.validation.BrandUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BrandUpdateRequestDto(
    @NotBlank(groups = {BrandUpdate.class}, message = "The field is required")
    @Size(max = 50, groups = {BrandUpdate.class}, message = "The field must be less than 50 characters")
    String name
) {
}
