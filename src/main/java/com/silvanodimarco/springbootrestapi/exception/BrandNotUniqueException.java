package com.silvanodimarco.springbootrestapi.exception;

import com.silvanodimarco.springbootrestapi.exception.error.ResourceNotUniqueException;

public class BrandNotUniqueException extends ResourceNotUniqueException {
    private static final String RESOURCE_NAME = "brand";

    public BrandNotUniqueException(String fieldName, Object fieldValue) {
        super(RESOURCE_NAME, fieldName, fieldValue);
    }
}
