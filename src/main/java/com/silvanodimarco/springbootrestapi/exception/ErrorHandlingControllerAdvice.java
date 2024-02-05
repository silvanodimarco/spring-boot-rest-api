package com.silvanodimarco.springbootrestapi.exception;

import com.silvanodimarco.springbootrestapi.api.dto.ApiErrorDto;
import com.silvanodimarco.springbootrestapi.api.dto.ApiValidationErrorDto;
import com.silvanodimarco.springbootrestapi.enums.ErrorMessages;
import com.silvanodimarco.springbootrestapi.exception.error.ResourceNotFoundException;
import com.silvanodimarco.springbootrestapi.exception.error.ResourceNotUniqueException;
import com.silvanodimarco.springbootrestapi.exception.model.ValidationError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ApiValidationErrorDto handleConstraintValidationException(ConstraintViolationException ex, HttpServletRequest request) {
        List<ValidationError> validations = new ArrayList<>();
        for (ConstraintViolation violation : ex.getConstraintViolations()) {
            validations.add(
                new ValidationError(violation.getPropertyPath().toString(), violation.getMessage()));
        }

        return ApiValidationErrorDto.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error(HttpStatus.BAD_REQUEST)
            .path(request.getRequestURI())
            .message(ErrorMessages.VALIDATION_FAILURE.getValue())
            .validations(validations)
            .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ApiValidationErrorDto handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<ValidationError> validations = new ArrayList<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            validations.add(new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        return ApiValidationErrorDto.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error(HttpStatus.BAD_REQUEST)
            .path(request.getRequestURI())
            .message(ErrorMessages.VALIDATION_FAILURE.getValue())
            .validations(validations)
            .build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiErrorDto handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        return ApiErrorDto.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.NOT_FOUND.value())
            .error(HttpStatus.NOT_FOUND)
            .path(request.getRequestURI())
            .message(ex.getMessage())
            .build();
    }

    @ExceptionHandler(ResourceNotUniqueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiErrorDto handleResourceNotUniqueException(ResourceNotUniqueException ex, HttpServletRequest request) {
        return ApiErrorDto.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error(HttpStatus.BAD_REQUEST)
            .path(request.getRequestURI())
            .message(ex.getMessage())
            .build();
    }
}
