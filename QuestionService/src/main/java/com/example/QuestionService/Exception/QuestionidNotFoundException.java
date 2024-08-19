package com.example.QuestionService.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class QuestionidNotFoundException extends RuntimeException {


    public QuestionidNotFoundException(String message) {
        super(message);
    }
}
