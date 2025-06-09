package com.service.streaming.core.exceptions;

public class GlobalDurinStreamingServiceException extends RuntimeException{

    public GlobalDurinStreamingServiceException() {
        super();
    }

    public GlobalDurinStreamingServiceException(String message) {
        super(message);
    }

    public GlobalDurinStreamingServiceException(String message, Throwable throwable) {
        super(message,throwable);
    }

    public GlobalDurinStreamingServiceException(Throwable throwable) {
        super(throwable);
    }
}
