package com.service.notification.core.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalDurinNotificationServiceExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalDurinNotificationServiceExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorInfo> handleRuntimeExceptionForOthers(HttpServletRequest req, RuntimeException ex) {
        LOGGER.error("GlobalDurinNotificationServiceException occurred: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED).body(new ErrorInfo(req.getRequestURL().toString(), ex.getLocalizedMessage(), String.valueOf(ex.getMessage()), new Date()));
    }

    @ExceptionHandler(GlobalDurinNotificationServiceException.class)
    public ResponseEntity<ErrorInfo> handlerGlobalDurinUserServiceException(GlobalDurinNotificationServiceException globalDurinNotificationServiceException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(
                null,
                "GlobalDurinNotificationServiceException",
                globalDurinNotificationServiceException.getMessage(),
                new Date()
        ));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorInfo> handleNullPointerException(HttpServletRequest httpServletRequest, NullPointerException nullPointerException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(
                httpServletRequest.getRequestURL().toString(),
                "NullPointerException",
                nullPointerException.getMessage(),
                new Date()
        ));
    }
}
