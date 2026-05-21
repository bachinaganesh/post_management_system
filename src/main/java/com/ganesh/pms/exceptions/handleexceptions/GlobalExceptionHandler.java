package com.ganesh.pms.exceptions.handleexceptions;

import com.ganesh.pms.dtos.responses.APIError;
import com.ganesh.pms.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIError>  handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {
        APIError apiError = APIError.builder()
                .message(e.getMessage())
                .path(request.getRequestURI())
                .status(HttpStatus.NOT_FOUND)
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
