package com.example.Survey_Service.Exception;

public class ExternalServiceException extends RuntimeException {

    // Constructor with a message
    public ExternalServiceException(String message) {
        super(message);
    }

    // Constructor with a cause
    public ExternalServiceException(Throwable cause) {
        super(cause);
    }

    // Constructor with both message and cause
    public ExternalServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

