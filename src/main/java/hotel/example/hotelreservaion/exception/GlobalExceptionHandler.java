package hotel.example.hotelreservaion.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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


}
