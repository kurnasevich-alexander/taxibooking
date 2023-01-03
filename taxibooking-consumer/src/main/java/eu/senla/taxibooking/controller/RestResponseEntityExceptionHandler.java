package eu.senla.taxibooking.controller;

import eu.senla.taxibooking.exception.EntityNotFoundException;
import eu.senla.taxibooking.model.exception.ApiErrorDto;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> springHandleNotFound(EntityNotFoundException ex) {
        ApiErrorDto error = ApiErrorDto.builder()
                .message(ex.getMessage())
                .timestamp(OffsetDateTime.now())
                .statusCode(HttpStatus.NOT_FOUND.value()).build();
        log.error("Exception was caught in RestResponseEntityExceptionHandler: ", ex);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> springHandleException(Exception ex) {
        ApiErrorDto error = ApiErrorDto.builder()
                .message(ex.getMessage())
                .timestamp(OffsetDateTime.now())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        log.error(String.valueOf(ex));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
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
                .statusCode(HttpStatus.BAD_REQUEST.value()).build();
        return new ResponseEntity<>(error, status);
    }
}
