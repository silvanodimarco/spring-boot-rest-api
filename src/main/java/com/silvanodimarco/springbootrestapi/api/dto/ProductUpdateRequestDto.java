package com.silvanodimarco.springbootrestapi.api.dto;

import com.silvanodimarco.springbootrestapi.api.validation.ProductUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductUpdateRequestDto(
    @NotBlank(groups = {ProductUpdate.class}, message = "The field is required")
    @Size(max = 100, groups = {ProductUpdate.class}, message = "The field must be less than 100 characters")
    String name
) {
}
