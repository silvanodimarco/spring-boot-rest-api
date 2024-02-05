package com.silvanodimarco.springbootrestapi.api.dto;

import com.silvanodimarco.springbootrestapi.exception.model.ValidationError;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ApiValidationErrorDto (
    LocalDateTime timestamp,
    Integer status,
    HttpStatus error,
    String message,
    String path,
    List<ValidationError> validations
) { }
