package com.yostoya.shoptill.exception;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.yostoya.shoptill.domain.HttpResponse;
import com.yostoya.shoptill.domain.dto.InvalidFieldDto;
import com.yostoya.shoptill.exception.alreadyexist.AlreadyExistException;
import com.yostoya.shoptill.exception.notfound.NotFoundException;
import com.yostoya.shoptill.mapper.FieldErrorMapper;
import com.yostoya.shoptill.mapper.ViolationMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;


@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApiExceptionHandler {


    private static final String INVALID_FIELDS = "One or more invalid fields.";
    private final FieldErrorMapper fieldErrorMapper;
    private final ViolationMapper violationMapper;

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<HttpResponse> handleConstraintViolationException(ConstraintViolationException ex,
                                                                           HttpServletRequest request) {

        return ResponseEntity.badRequest().body(
                new HttpResponse(BAD_REQUEST, INVALID_FIELDS, Map.of("errors", mapViolatedFields(ex))));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<HttpResponse> handleBindException(BindException ex, HttpServletRequest request) {

        return ResponseEntity.badRequest().body(
                new HttpResponse(BAD_REQUEST, INVALID_FIELDS, Map.of("errors", mapErrorFields(ex))));
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<HttpResponse> handleTokenExpiredException(TokenExpiredException e) {
        return new ResponseEntity<>(getHttpException("Unable to validate token", INTERNAL_SERVER_ERROR),
                INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidationException.class)
    private ResponseEntity<HttpResponse> handleExceptionNotFound(ValidationException e) {
        return ResponseEntity.badRequest().body(getHttpException(e.getMessage(), BAD_REQUEST));
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    private ResponseEntity<Object> handleExceptionHttpMessageConversionException(HttpMessageConversionException e) {
        return new ResponseEntity<>(getHttpException(e.getMessage(), BAD_REQUEST), BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<HttpResponse> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getResponse(), NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<HttpResponse> handleAlreadyExistException(AlreadyExistException ex) {
        return new ResponseEntity<>(ex.getResponse(), BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<HttpResponse> sQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException exception) {

        log.error(exception.getMessage());
        final String reason = exception.getMessage().contains("Duplicate entry") ?
                "Information already exists" :
                exception.getMessage();

        return new ResponseEntity<>(getHttpException(
                reason, BAD_REQUEST), BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> badCredentialsException(BadCredentialsException exception) {

        log.error(exception.getMessage());
        return new ResponseEntity<>(getHttpException(
                exception.getMessage() + ", Incorrect email or password", BAD_REQUEST), BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpResponse> accessDeniedException(AccessDeniedException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(getHttpException(
                "Access denied. You don't have access", FORBIDDEN), FORBIDDEN);
    }

    @ExceptionHandler(JWTDecodeException.class)
    public ResponseEntity<HttpResponse> exception(JWTDecodeException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(getHttpException(
                "Could not decode the token", INTERNAL_SERVER_ERROR), INTERNAL_SERVER_ERROR);

    }

    private HttpResponse getHttpException(String message, HttpStatus status) {
        return HttpResponse.builder()
                .timestamp(now().toString())
                .message(message)
                .status(status)
                .statusCode(status.value())
                .build();
    }

    private List<InvalidFieldDto> mapErrorFields(BindException ex) {
        return ex.getFieldErrors().stream().map(fieldErrorMapper::toDto).collect(Collectors.toList());
    }

    private List<InvalidFieldDto> mapViolatedFields(ConstraintViolationException ex) {
        return ex.getConstraintViolations().stream().map(violationMapper::toDto).collect(Collectors.toList());
    }
}
