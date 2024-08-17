package com.example.Survey_Service.Exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        // Handle different status codes
        HttpStatus status = HttpStatus.valueOf(response.status());
        return switch (status) {
            case NOT_FOUND -> new ResourceNotFoundException("Resource not found: " + methodKey);
            case BAD_REQUEST -> new BadRequestException("Bad request: " + methodKey);
            default -> new FeignException("Feign error: " + methodKey);
        };
    }
}
