package eu.senla.taxibooking_producer.controller;

import eu.senla.taxibooking_model.exception.ApiErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

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
