package com.backend.MyBackend.exception;

import com.backend.MyBackend.cart.exception.ActiveCartNotFoundException;
import com.backend.MyBackend.cart.exception.CartAlreadyExistsException;
import com.backend.MyBackend.common.dto.ApiResponse;
import com.backend.MyBackend.email.exceptions.EmailNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex){

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(
                        error.getField(),
                        error.getDefaultMessage()));

        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ActiveCartNotFoundException.class)
    public ResponseEntity<ApiResponse> handleActiveCartNotFound(
            ActiveCartNotFoundException ex){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse(ex.getMessage(),null));
    }

    @ExceptionHandler(CartAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleCartAlreadyExists(
            CartAlreadyExistsException ex){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse(ex.getMessage(),null));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUserNotFound(UserNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse(ex.getMessage(),null));
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ApiResponse> handleEmailNotFound(EmailNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(ex.getMessage(),null));
    }

    @ExceptionHandler(UserIdRequiredException.class)
    public ResponseEntity<ApiResponse> handleUseridRequired(UserIdRequiredException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(ex.getMessage(),null));
    }

}
