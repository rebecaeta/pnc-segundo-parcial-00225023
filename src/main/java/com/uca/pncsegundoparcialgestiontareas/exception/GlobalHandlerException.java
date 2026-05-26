package com.uca.pncsegundoparcialgestiontareas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleTaskNotFound(TaskNotFound taskNotFound){
        return new ResponseEntity<>(ApiError.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(taskNotFound.getMessage())
                .status(404)
                .build(), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusinessException(BusinessException businessException){
        return new ResponseEntity<>(ApiError.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(businessException.getMessage())
                .status(400)
                .build(), HttpStatus.BAD_REQUEST
        );
    }

}
