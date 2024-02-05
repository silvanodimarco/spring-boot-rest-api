package com.silvanodimarco.springbootrestapi.enums;

public enum ErrorMessages {
    VALIDATION_FAILURE("Validations failed for the request.");

    private final String value;

    ErrorMessages(String value) {
        this.value = value;
    }

    public final String getValue() {
        return this.value;
    }
}
