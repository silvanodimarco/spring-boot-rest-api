package com.silvanodimarco.springbootrestapi.exception;

import com.silvanodimarco.springbootrestapi.exception.error.ResourceNotFoundException;

public class ProductNotFoundException extends ResourceNotFoundException {
    private static final String RESOURCE_NAME = "product";

    public ProductNotFoundException(String fieldName, Object fieldValue) {
        super(RESOURCE_NAME, fieldName, fieldValue);
    }
}
