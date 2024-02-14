package com.silvanodimarco.springbootrestapi.exception;

import com.silvanodimarco.springbootrestapi.exception.error.ResourceNotFoundException;

public class BrandNotFoundException extends ResourceNotFoundException {
    private static final String RESOURCE_NAME = "brand";

    public BrandNotFoundException(String fieldName, Object fieldValue) {
        super(RESOURCE_NAME, fieldName, fieldValue);
    }
}
