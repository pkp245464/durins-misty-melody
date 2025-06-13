package com.service.recommendation.core.exceptions;

public class GlobalDurinRecommendationServiceException extends RuntimeException{

    public GlobalDurinRecommendationServiceException() {
        super();
    }

    public GlobalDurinRecommendationServiceException(String message) {
        super(message);
    }

    public GlobalDurinRecommendationServiceException(String message, Throwable throwable) {
        super(message,throwable);
    }

    public GlobalDurinRecommendationServiceException(Throwable throwable) {
        super(throwable);
    }
}
