package hotel.example.hotelreservaion.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import hotel.example.hotelreservaion.dto.ApiError;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(HttpMessageNotReadableException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("error", "Corps de requête manquant ou JSON invalide");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {

    Map<String, Object> body = new HashMap<>();
    Map<String, String> fieldErrors = new HashMap<>();

    ex.getBindingResult().getFieldErrors().forEach(error -> {
        fieldErrors.put(error.getField(), error.getDefaultMessage());
    });

    body.put("error", "Validation failed");
    body.put("details", fieldErrors);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
}

@ExceptionHandler(CustomException.class)
public ResponseEntity<ApiError> handleCustomException(CustomException ex) {
    ApiError error = new ApiError(false, ex.getMessage(), LocalDateTime.now());
    return ResponseEntity.status(ex.getStatus()).body(error);
}
    
}
