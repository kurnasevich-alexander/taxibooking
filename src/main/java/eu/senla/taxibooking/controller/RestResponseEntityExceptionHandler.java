package eu.senla.taxibooking.controller;

import eu.senla.taxibooking.exception.ApiErrorDto;
import eu.senla.taxibooking.exception.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> springHandleNotFound(EntityNotFoundException ex) {
        ApiErrorDto error = ApiErrorDto.builder()
                .message(ex.getMessage())
                .timestamp(OffsetDateTime.now())
                .status(HttpStatus.NOT_FOUND).build();
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> springHandleException(Exception ex) {
        ApiErrorDto error = ApiErrorDto.builder()
                .message(ex.getMessage())
                .timestamp(OffsetDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return new ResponseEntity<>(error, error.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String message = fieldErrors.stream()
                .map(fieldError -> fieldError.getField()
                        .concat(":")
                        .concat(Objects.requireNonNull(fieldError.getDefaultMessage())))
                .collect(Collectors.joining(", "));
        ApiErrorDto error = ApiErrorDto.builder()
                .message(message)
                .timestamp(OffsetDateTime.now())
                .status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<>(error, status);
    }
}
