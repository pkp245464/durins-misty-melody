package com.service.analytics.core.exceptions;

public class GlobalDurinAnalyticsServiceException extends RuntimeException{
    public GlobalDurinAnalyticsServiceException() {
        super();
    }

    public GlobalDurinAnalyticsServiceException(String message) {
        super(message);
    }

    public GlobalDurinAnalyticsServiceException(String message, Throwable throwable) {
        super(message,throwable);
    }

    public GlobalDurinAnalyticsServiceException(Throwable throwable) {
        super(throwable);
    }
}
