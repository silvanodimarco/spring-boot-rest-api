package com.silvanodimarco.springbootrestapi.api.dto;

import com.silvanodimarco.springbootrestapi.api.validation.ReviewUpdate;
import jakarta.validation.constraints.*;

public record ReviewUpdateRequestDto(
    @Size(max = 140, groups = {ReviewUpdate.class}, message = "The field must be less than 140 characters")
    String content,

    @Min(groups = {ReviewUpdate.class}, value = 1, message = "The field must be equal or greater than 1")
    @Max(groups = {ReviewUpdate.class}, value = 10, message = "The field must be equal or less than 10")
    Integer rating,

    Boolean active
) {
}
