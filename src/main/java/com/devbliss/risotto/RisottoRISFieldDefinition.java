package com.devbliss.risotto;

public enum RisottoRISFieldDefinition {

    // TODO this isn't complete:
    FIELD_SECONDARY_AUTHOR("A2"),
    FIELD_TERTIARY_AUTHOR("A3"),
    FIELD_SUBSIDIARY_AUTHOR("A4");

    private final String fieldName;

    RisottoRISFieldDefinition(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
