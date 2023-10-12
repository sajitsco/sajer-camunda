package com.sajits.sajer.samo.domain.enumeration;

public enum OperatorType {
    EQ,
    NE,
    GE,
    BETWEEN,
    GT,
    ILIKE,
    LE,
    LT,
    LIKE,
    IN,
    IS_NULL,
    IS_NOT_NULL,
    PATTERN;

    private OperatorType() {
    }
}
