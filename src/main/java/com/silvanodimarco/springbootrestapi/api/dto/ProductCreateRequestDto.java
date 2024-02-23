package com.silvanodimarco.springbootrestapi.api.dto;

import com.silvanodimarco.springbootrestapi.api.validation.ProductCreate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductCreateRequestDto(
    @NotBlank(groups = {ProductCreate.class}, message = "The field is required")
    @Size(max = 100, groups = {ProductCreate.class}, message = "The field must be less than 100 characters")
    String name,

    @NotNull(groups = {ProductCreate.class}, message = "The field is required")
    Integer brandId
) {}
