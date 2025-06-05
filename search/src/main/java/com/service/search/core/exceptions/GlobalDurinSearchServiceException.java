package com.service.search.core.exceptions;

public class GlobalDurinSearchServiceException extends RuntimeException{
    public GlobalDurinSearchServiceException() {
        super();
    }

    public GlobalDurinSearchServiceException(String message) {
        super(message);
    }

    public GlobalDurinSearchServiceException(String message, Throwable throwable) {
        super(message,throwable);
    }

    public GlobalDurinSearchServiceException(Throwable throwable) {
        super(throwable);
    }
}
