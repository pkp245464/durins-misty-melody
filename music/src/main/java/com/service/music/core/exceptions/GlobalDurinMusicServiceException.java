package com.service.music.core.exceptions;


public class GlobalDurinMusicServiceException extends RuntimeException {
    public GlobalDurinMusicServiceException() {
        super();
    }

    public GlobalDurinMusicServiceException(String message) {
        super(message);
    }

    public GlobalDurinMusicServiceException(String message, Throwable throwable) {
        super(message,throwable);
    }

    public GlobalDurinMusicServiceException(Throwable throwable) {
        super(throwable);
    }
}
