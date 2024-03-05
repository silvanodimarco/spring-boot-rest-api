package com.silvanodimarco.springbootrestapi.exception;

import com.silvanodimarco.springbootrestapi.exception.error.ResourceNotFoundException;

public class ReviewNotFoundException extends ResourceNotFoundException {
    private static final String RESOURCE_NAME = "review";

    public ReviewNotFoundException(String fieldName, Object fieldValue) {
        super(RESOURCE_NAME, fieldName, fieldValue);
    }
}
