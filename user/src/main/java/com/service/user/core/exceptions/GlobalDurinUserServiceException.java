package com.service.user.core.exceptions;

public class GlobalDurinUserServiceException extends RuntimeException{

    public GlobalDurinUserServiceException() {
        super();
    }

    public GlobalDurinUserServiceException(String message) {
        super(message);
    }

    public GlobalDurinUserServiceException(String message, Throwable throwable) {
        super(message,throwable);
    }

    public GlobalDurinUserServiceException(Throwable throwable) {
        super(throwable);
    }
}
