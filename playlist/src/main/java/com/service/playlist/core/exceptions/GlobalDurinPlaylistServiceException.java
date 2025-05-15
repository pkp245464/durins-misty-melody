package com.service.playlist.core.exceptions;

public class GlobalDurinPlaylistServiceException extends RuntimeException{

    public GlobalDurinPlaylistServiceException() {
        super();
    }

    public GlobalDurinPlaylistServiceException(String message) {
        super(message);
    }

    public GlobalDurinPlaylistServiceException(String message, Throwable throwable) {
        super(message,throwable);
    }

    public GlobalDurinPlaylistServiceException(Throwable throwable) {
        super(throwable);
    }
}
