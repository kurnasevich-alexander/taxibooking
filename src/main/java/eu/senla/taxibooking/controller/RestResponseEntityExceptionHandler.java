package eu.senla.taxibooking.controller;

import eu.senla.taxibooking.dto.ApiErrorDTO;
import eu.senla.taxibooking.exception.BookingNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<Object> springHandleNotFound(BookingNotFoundException ex) {
        ApiErrorDTO error = ApiErrorDTO.builder()
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND).build();
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> springHandleException(Exception ex) {
        ApiErrorDTO error = ApiErrorDTO.builder()
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return new ResponseEntity<>(error, error.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return ResponseEntity.status(status)
                .body(fieldErrors.toArray(new FieldError[fieldErrors.size()]));
    }
}
