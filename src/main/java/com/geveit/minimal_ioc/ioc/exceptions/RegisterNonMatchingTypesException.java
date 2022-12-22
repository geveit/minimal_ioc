package com.geveit.minimal_ioc.ioc.exceptions;

public class RegisterNonMatchingTypesException extends RuntimeException {
    public <T, I> RegisterNonMatchingTypesException(Class<T> type, Class<I> implementationType) {
        super(implementationType.getName() + " is not a subtype of " + type.getName());
    }
}
