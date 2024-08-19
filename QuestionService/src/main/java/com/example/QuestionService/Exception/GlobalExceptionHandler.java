package com.example.QuestionService.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private  final String setAlreadyExists = "Set id not found";

    @ExceptionHandler(QuestionidNotFoundException.class)
    public ResponseEntity<String> handleQuestionidNotFoundException(
            QuestionidNotFoundException exception) {
        return ResponseEntity.ok(exception.getMessage()); // 200 with a custom message
    }


    @ExceptionHandler(SetidNotFoundException.class)
    public ResponseEntity<String> handleSetidNotFoundException(
            SetidNotFoundException exception) {

        return ResponseEntity.ok(setAlreadyExists);
    }

    @ExceptionHandler(AssessmentNotFoundException.class)
    public ResponseEntity<String> handleAssessmentNotFoundException(
            AssessmentNotFoundException exception) {
        return ResponseEntity.ok(exception.getMessage()); // 200 with a custom message
    }
}
