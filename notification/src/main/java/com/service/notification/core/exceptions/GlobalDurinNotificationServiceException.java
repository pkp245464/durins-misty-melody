package com.service.notification.core.exceptions;

public class GlobalDurinNotificationServiceException extends RuntimeException {
    public GlobalDurinNotificationServiceException() {
        super();
    }

    public GlobalDurinNotificationServiceException(String message) {
        super(message);
    }

    public GlobalDurinNotificationServiceException(String message, Throwable throwable) {
        super(message,throwable);
    }

    public GlobalDurinNotificationServiceException(Throwable throwable) {
        super(throwable);
    }
}
