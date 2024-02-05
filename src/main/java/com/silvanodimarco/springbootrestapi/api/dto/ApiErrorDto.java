package com.silvanodimarco.springbootrestapi.api.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
public record ApiErrorDto (
    LocalDateTime timestamp,
    Integer status,
    HttpStatus error,
    String message,
    String path
) {}
