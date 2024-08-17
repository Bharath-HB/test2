package com.example.Survey_Service.Exception;

public class FeignException extends RuntimeException {
    public FeignException(String message) {
        super(message);
    }
}

