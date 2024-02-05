package com.silvanodimarco.springbootrestapi.exception.error;

public class ResourceNotUniqueException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public ResourceNotUniqueException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("Resource %s exists with %s: '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
