package com.silvanodimarco.springbootrestapi.api.dto;

import com.silvanodimarco.springbootrestapi.api.validation.ReviewCreate;
import jakarta.validation.constraints.*;

public record ReviewCreateRequestDto(
    @NotBlank(groups = {ReviewCreate.class}, message = "The field is required")
    @Size(max = 140, groups = {ReviewCreate.class}, message = "The field must be less than 140 characters")
    String content,

    @Min(groups = {ReviewCreate.class}, value=1, message="The field must be equal or greater than 1")
    @Max(groups = {ReviewCreate.class}, value=10, message="The field must be equal or less than 10")
    Integer rating,

    @NotNull(groups = {ReviewCreate.class}, message = "The field is required")
    Integer productId
) {
}
