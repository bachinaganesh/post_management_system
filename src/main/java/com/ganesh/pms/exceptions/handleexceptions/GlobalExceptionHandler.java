package com.ganesh.pms.exceptions.handleexceptions;

import com.ganesh.pms.dtos.responses.APIError;
import com.ganesh.pms.exceptions.NoSessionFoundException;
import com.ganesh.pms.exceptions.ResourceAlreadyExistedException;
import com.ganesh.pms.exceptions.ResourceNotFoundException;
import com.ganesh.pms.exceptions.RoleAlreadyAssignedException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIError>  handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {
        APIError apiError = APIError.builder()
                .message(e.getMessage())
                .path(request.getRequestURI())
                .status(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIError>  handleException(Exception e, HttpServletRequest request) {
        APIError apiError = APIError.builder()
                .message(e.getMessage())
                .path(request.getRequestURI())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<APIError>  handleJwtException(JwtException e, HttpServletRequest request) {
        APIError apiError = APIError.builder()
                .message(e.getMessage())
                .path(request.getRequestURI())
                .status(HttpStatus.UNAUTHORIZED)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<APIError>  handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        APIError apiError = APIError.builder()
                .message(e.getMessage())
                .path(request.getRequestURI())
                .status(HttpStatus.FORBIDDEN)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ResourceAlreadyExistedException.class)
    public ResponseEntity<APIError>  handleResourceAlreadyExistedException(ResourceAlreadyExistedException e, HttpServletRequest request) {
        APIError apiError = APIError.builder()
                .message(e.getMessage())
                .path(request.getRequestURI())
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleAlreadyAssignedException.class)
    public ResponseEntity<APIError>  handleRoleAlreadyAssignedException(RoleAlreadyAssignedException e, HttpServletRequest request) {
        APIError apiError = APIError.builder()
                .message(e.getMessage())
                .path(request.getRequestURI())
                .status(HttpStatus.CONFLICT)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoSessionFoundException.class)
    public ResponseEntity<APIError>  handleNoSessionFoundException(NoSessionFoundException e, HttpServletRequest request) {
        APIError apiError = APIError.builder()
                .message(e.getMessage())
                .path(request.getRequestURI())
                .status(HttpStatus.UNAUTHORIZED)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }
}
